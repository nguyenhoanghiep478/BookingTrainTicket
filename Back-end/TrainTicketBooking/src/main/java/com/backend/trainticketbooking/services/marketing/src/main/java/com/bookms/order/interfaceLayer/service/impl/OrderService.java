package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.*;
import com.bookms.order.application.servicegateway.IAuthServiceGateway;
import com.bookms.order.application.usecase.impl.FindBookUtils;
import com.bookms.order.interfaceLayer.DTO.*;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.IFindOrderService;
import com.bookms.order.interfaceLayer.service.IOrderService;
import com.bookms.order.core.domain.Exception.BookNotFoundException;
import com.bookms.order.core.domain.Exception.InSufficientQuantityException;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {
    private final ICreateOrderService createOrderService;
    private final IFindOrderService findOrderService;
    private final ModelMapper modelMapper;
    private final IAuthServiceGateway authServiceGateway;
    private final FindBookUtils findBookUtils;

    @Override
    public List<OrderDTO> findAll() {
        List<OrderDTO> result = new ArrayList<>();
        List<Orders> orders = findOrderService.findAll();
        if(orders.isEmpty()) {
           throw new OrderNotFoundException("cannot find any orders");
        }
        for (Orders order : orders) {
            result.add(modelMapper.map(order, OrderDTO.class));
        }
       return result;
    }

    @Override
    public OrderDTO findById(int id) {
        try {
            return modelMapper.map(findOrderService.findById(id),OrderDTO.class);
        }catch (OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(OrdersModel request) {
        try {
           return OrderMapper.toDTO(createOrderService.createOrder(request));
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException(String.format("Book with id %s not found", request.getOrderItems().get(0).getBookId()));
        } catch (InSufficientQuantityException e) {
            throw new InSufficientQuantityException(e.getMessage());
        } catch (OrderExistException e) {
            throw new OrderExistException(e.getMessage());
        }
    }
    @Override
    public OrderDTO findByOrderNumber(Long orderNumber) {
        return modelMapper.map(findOrderService.findByOrderNumber(orderNumber),OrderDTO.class);
    }

    @Override
    public PaymentModel prePay(OrderDTO request) {
        return createOrderService.prePayment(request);
    }

    @Override
    public OrdersModel afterPay(Long orderNumber) {
        return createOrderService.afterPayment(orderNumber);
    }

    @Override
    public void completeOrder(Long key) {
        createOrderService.completeOrder(key);
    }

    @Override
    public void handlePaymentResponse(OrderDTO orderDTO) {
        log.info(String.valueOf(orderDTO.getCustomerId()));
    }

    @Override
    public List<OrderDTO> findLatest(int amount) {
        List<Orders> orders = findOrderService.findLatest(amount);
        List<OrderDTO> result = new ArrayList<>();
        for(Orders order : orders) {
            result.add(modelMapper.map(order, OrderDTO.class));
        }
        for(OrderDTO order : result) {
            CustomerModel customer= authServiceGateway.findCustomerById(order.getCustomerId());
            order.setCustomerName(customer.getFirstName()+ " " + customer.getLastName());
        }
        return result;
    }

    @Override
    public OrdersModel handleOrderWasPaid(ResponsePayment responsePayment) {
        return createOrderService.handleOrderWasPaid(responsePayment);
    }

    @Override
    public List<TopSaleDTO> getTopSale() {
        return findOrderService.getTopSales();
    }

    @Override
    public List<ChartDTO> getChartOrderInWeek() {
        return findOrderService.getChartOrderInWeek();

    }

    @Override
    public OrdersModel handleCodPaymentMethod(OrderDTO request) {
        OrdersModel model = modelMapper.map(request,OrdersModel.class);
        return createOrderService.handleCodOrder(model);
    }

    @Override
    public List<OrderDTO> findByCustomerId(int id) {
        return findOrderService.findByCustomerId(id).stream()
                .map(orders -> {
                    OrdersModel model = modelMapper.map(orders,OrdersModel.class);
                    model.setOrderItems(findBookUtils.toSet(model.getOrderItems()));
                    List<BookModel> bookModels = findBookUtils.getBookModels(model);
                    for(int i = 0 ; i < bookModels.size() ; i++){
                        model.getOrderItems().get(i).setName(bookModels.get(i).getName());
                        model.getOrderItems().get(i).setPrice(bookModels.get(i).getPrice());
                        model.getOrderItems().get(i).setTotalQuantity(orders.getOrderItems().get(i).getTotalQuantity());
                    }
                    return modelMapper.map(model,OrderDTO.class);
                })
                .toList();


    }




}
