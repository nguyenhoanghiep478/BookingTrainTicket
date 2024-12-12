package com.backend.store.interfacelayer.service.statistic.impl;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.repository.ITicketRepository;
import com.backend.store.interfacelayer.dto.objectDTO.*;
import com.backend.store.interfacelayer.service.statistic.IStatisticService;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final ITicketRepository ticketRepository;
    private final IFindTicketService findTicketService;
    private final ITicketService ticketService;

    @Override
    public List<StatisticRevenueInDateDTO> getStatisticRevenueInDate(int year,int month,int day) {

        List<Object[]> objects = ticketRepository.findHourlyRevenueByDate( year,month,day);
        List<StatisticRevenueInDateDTO> statisticRevenueInDateDTOList = new ArrayList<>();
        for (Object[] row : objects) {
            String hourLabel = (String) row[0];
            StatisticRevenueInDateDTO dto = StatisticRevenueInDateDTO.builder()
                    .hours(Hour.fromHourLabel(hourLabel))
                    .revenue((BigDecimal) row[1])
                    .amount((Long) row[2])
                    .build();
            statisticRevenueInDateDTOList.add(dto);
        }

        return statisticRevenueInDateDTOList;
    }

    @Override
    public List<StatisticRevenueInMonthDTO> getStatisticRevenueInMonth(int year, int month) {
       List<Object[]> objects = ticketRepository.findDatelyRevenueInMonth(year,month);
       List<StatisticRevenueInMonthDTO> statisticRevenueInMonthDTOList = new ArrayList<>();
        for (Object[] row : objects) {
            String dayLabel = (String) row[0]; // "YYYY-MM-DD"
            BigDecimal revenue = ((BigDecimal) row[1]); // Tổng doanh thu

            // Lấy ngày từ chuỗi dayLabel
            int day = Integer.parseInt(dayLabel.split("-")[2]); // Lấy phần ngày từ "YYYY-MM-DD"

            StatisticRevenueInMonthDTO dto = StatisticRevenueInMonthDTO.builder()
                    .revenue(revenue)
                    .day(DayInMonth.fromDay(day))
                    .amount((Long) row[2])
                    .build();
            statisticRevenueInMonthDTOList.add(dto);
        }
       return statisticRevenueInMonthDTOList;
    }

    @Override
    public List<StatisticRevenueInYearDTO> getStatisticRevenueInYear(int year) {
        List<Object[]> objects = ticketRepository.findDatelyRevenueInYear(year);
        List<StatisticRevenueInYearDTO> statisticRevenueInYearDTOList = new ArrayList<>();
        for (Object[] row : objects) {
            String monthLabel = (String) row[0];
            BigDecimal revenue = ((BigDecimal) row[1]);

            int monthNumber = Integer.parseInt(monthLabel.split("-")[1]);
            MonthInYear monthEnum = MonthInYear.fromMonthNumber(monthNumber);

            StatisticRevenueInYearDTO dto = StatisticRevenueInYearDTO.builder()
                    .month(monthEnum)
                    .revenue(revenue)
                    .amount((Long) row[2])
                    .build();
            statisticRevenueInYearDTOList.add(dto);
        }
        return statisticRevenueInYearDTOList;
    }

    @Override
    public StatisticTicketByCustomerDTO getTicketByCustomerName(String customerName) {
        List<Ticket> tickets = findTicketService.findTicketByCustomerName(customerName);
        return StatisticTicketByCustomerDTO.builder()
                .tickets(getTicketDTOs(tickets))
                .customerName(customerName)
                .build();
    }

    @Override
    public StatisticTicketByCustomerDTO getTicketByCustomerNameAndDeparture(String customerName, String departureStationName) {
        List<Ticket> tickets = findTicketService.findTicketByCustomerName(customerName);
        tickets = tickets.stream().filter(ticket -> ticket.getDepartureStation().getName().equals( departureStationName)).toList();

        return StatisticTicketByCustomerDTO.builder()
                .tickets(getTicketDTOs(tickets))
                .customerName(customerName)
                .build();
    }


    private List<TicketDTO> getTicketDTOs(List<Ticket> tickets){
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketDTO ticketDTO = ticketService.toDTO(ticket);
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }


}
