package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.SeatModel;
import com.bookms.order.infrastructure.FeignClient.StoreClient;
import com.bookms.order.infrastructure.serviceGateway.IStoreServiceGateway;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceGateway implements IStoreServiceGateway {
    private final StoreClient storeClient;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;


    @Override
    public List<SeatModel> getSeatsAvailableInSystem(OrdersModel orders) {
        try{
           List<Integer> seatIds = orders.getOrderItems().stream().map(OrderItemModel::getSeatId).toList();
           ResponseEntity<ResponseDTO> response = storeClient.getSeatAvailableById(seatIds,orders.getScheduleId(), orders.getDepartureStationId());
           ResponseDTO responseDTO = response.getBody();
            List<SeatModel> seatModels = null;
            Map<List<SeatModel>, Object> map = null;
            if (responseDTO != null && responseDTO.getResult() != null) {
                seatModels = modelMapper.map(responseDTO.getResult(), List.class);
                seatModels = objectMapper.convertValue(seatModels, objectMapper.getTypeFactory().constructCollectionType(List.class, SeatModel.class));
            }
            return seatModels;

        }catch (Error e){
            log.error(e.getMessage());

        }
        return null;
    }
}
