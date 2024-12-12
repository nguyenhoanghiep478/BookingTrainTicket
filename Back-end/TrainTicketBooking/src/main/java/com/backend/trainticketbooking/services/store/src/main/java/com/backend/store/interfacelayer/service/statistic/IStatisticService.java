package com.backend.store.interfacelayer.service.statistic;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.*;

import java.sql.Timestamp;
import java.util.List;

public interface IStatisticService {
    List<StatisticRevenueInDateDTO> getStatisticRevenueInDate(int year,int month,int day);

    List<StatisticRevenueInMonthDTO> getStatisticRevenueInMonth(int year, int month);

    List<StatisticRevenueInYearDTO> getStatisticRevenueInYear(int year);

    StatisticTicketByCustomerDTO getTicketByCustomerName(String customerName);

    StatisticTicketByCustomerDTO getTicketByCustomerNameAndDeparture(String customerName, String departureStationName);
}
