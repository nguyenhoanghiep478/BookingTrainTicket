package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.application.model.OrderSearchCriteria;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.application.usecase.impl.FindOrdersUseCase;
import com.bookms.order.application.usecase.IFindOrderUseCase;
import com.bookms.order.application.usecase.impl.FindSpecialFieldUseCase;
import com.bookms.order.application.usecase.impl.FindTopSalesUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.State.DateOfWeek;
import com.bookms.order.interfaceLayer.DTO.ChartDTO;
import com.bookms.order.interfaceLayer.DTO.CrawChartDTO;
import com.bookms.order.interfaceLayer.DTO.ProfitDTO;
import com.bookms.order.interfaceLayer.DTO.TopSaleDTO;
import com.bookms.order.interfaceLayer.service.IFindOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindOrderService implements IFindOrderService {
    private final IFindOrderUseCase findOrderUseCase;
    private final FindTopSalesUseCase findTopSalesUseCase;
    private final FindSpecialFieldUseCase findSpecialFieldUseCase;
    private final IBookServiceGateway bookServiceGateway;

    @Override
    public Orders findById(int id) {
        Criteria fieldId = Criteria.builder()
                .key("id")
                .operator("=")
                .value(id)
                .build();
        return findOrderUseCase.execute(List.of(fieldId)).get(0);
    }

    @Override
    public Orders findByOrderNumber(Long orderNumber) {
        Criteria fieldOrderNumber = Criteria.builder()
                .key("orderNumber")
                .operator("=")
                .value(orderNumber)
                .build();
        return findOrderUseCase.execute(List.of(fieldOrderNumber)).get(0);
    }

    @Override
    public List<Orders> findAll() {
        return findOrderUseCase.execute(null);
    }

    @Override
    public List<Orders> findLatest(int amount) {
        Criteria fieldCreatedDate = Criteria.builder()
                .key("createdDate")
                .operator("Order By")
                .build();
        Criteria fieldAmount = Criteria.builder()
                .operator("GET")
                .value(amount)
                .build();
        Criteria desc = Criteria.builder()
                .operator("DESC")
                .build();
        return findOrderUseCase.execute(List.of(fieldCreatedDate, fieldAmount,desc));
    }

    @Override
    public List<TopSaleDTO> getTopSales() {
        return findTopSalesUseCase.execute().stream()
                .map(item -> {
                    TopSaleDTO dto = new TopSaleDTO();
                    dto.setBookId(item);
                    return dto;
                }).toList();
    }

    @Override
    public List<ChartDTO> getChartOrderInWeek() {
        Criteria searchBySQl = Criteria.builder()
                .sql("select EXTRACT(DOW FROM o.created_date) as day_of_week,SUM(o.total_price) as total_payment,oi.book_id as book_id,sum(oi.total_quantity) as quantity\n" +
                        "from orders o\n" +
                        "join\n" +
                        "order_items oi on o.id = oi.order_id\n" +
                        "where o.created_date >= DATE_TRUNC('week',NOW()) and o.status = 'COMPLETED'\n" +
                        "AND o.created_date < DATE_TRUNC('week',NOW())+INTERVAL '1 week'\n" +
                        "GROUP BY EXTRACT(DOW FROM o.created_date), oi.book_id")
                .build();
       List<?> resultList = (List<?>) findSpecialFieldUseCase.execute(List.of(searchBySQl));
        log.info(resultList.toString());
        List<CrawChartDTO> crawData = resultList.stream()
                .filter(item -> item instanceof Object[])
                .map(item -> (Object[]) item)
                .map(array -> new CrawChartDTO(
                        DateOfWeek.fromValue(((Number) array[0]).intValue()), // day_of_week
                        new BigDecimal(array[1].toString()), // total_payment
                        ((Number) array[2]).intValue(), // book_id
                        ((Number) array[3]).intValue() // quantity
                ))
                .collect(Collectors.toList());


        Set<Integer> ids = crawData.stream().map(CrawChartDTO::getBookId).collect(Collectors.toSet());

        Map<Integer, BigDecimal> profits = bookServiceGateway.getProfitByIds(1,ids);

       return cleanCrawData(crawData,profits);
    }

    @Override
    public List<Orders> findByCustomerId(int id) {
        Criteria criteria = Criteria.builder()
                .key("customerId")
                .operator("=")
                .value(id)
                .build();
        return findOrderUseCase.execute(List.of(criteria));
    }


    @SafeVarargs
    private List<Criteria> getCriteria(Map<String,Object>... fields) {
        List<Criteria> criteriaList = new ArrayList<>();
        for (var field : fields) {

        }
        return criteriaList;
    }

    private List<ChartDTO> cleanCrawData(List<CrawChartDTO> crawData,Map<Integer, BigDecimal> profits) {
        Map<DateOfWeek,ChartDTO> flag = new HashMap<>();

        for (CrawChartDTO data : crawData){
            DateOfWeek dateOfWeek = data.getDateOfWeek();
            BigDecimal dataProfit = profits.get(data.getBookId()).multiply(BigDecimal.valueOf(data.getQuantity()));
            BigDecimal dataTotalPrice = data.getTotalPrice();

            if(flag.containsKey(dateOfWeek)){
                ChartDTO chartDTO = flag.get(dateOfWeek);
                BigDecimal currentProfit = chartDTO.getProfit();
                BigDecimal currentTotalPrice = chartDTO.getTotalPrice();
                chartDTO.setProfit(currentProfit.add(dataProfit));
                chartDTO.setTotalPrice(currentTotalPrice.add(dataTotalPrice));
            }else{
                flag.put(dateOfWeek, ChartDTO.builder()
                                .dateOfWeek(dateOfWeek)
                                .profit(dataProfit)
                                .totalPrice(data.getTotalPrice())
                        .build());
            }
        }

        return flag.values().stream().toList();
    }
}
