package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.UpdateQuantityModel;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.interfaceLayer.DTO.ProfitDTO;
import com.bookms.order.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.bookms.order.infrastructure.FeignClient.BookClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BookServiceGateway implements IBookServiceGateway {
    private final BookClient bookClient;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public BookModel updateQuantity(UpdateQuantityModel updateQuantityModel) {
        ResponseEntity<ResponseDTO> response = bookClient.updateQuantity(modelMapper.map(updateQuantityModel, UpdateQuantityDTO.class));
        ResponseDTO responseDTO = response.getBody();
        if(responseDTO != null){
            return modelMapper.map(responseDTO.getResult(), BookModel.class);
        }
        return null;
    }

    @Override
    public List<BookModel> findAllBookWithListId(Set<Integer> ids) {
        ResponseEntity<ResponseDTO> response = bookClient.getBooksByIds(ids);
        ResponseDTO responseDTO = response.getBody();
        List<BookModel> bookModels = null;

        Map<List<BookModel>, Object> map = null;
        if (responseDTO != null && responseDTO.getResult() != null) {
            bookModels = modelMapper.map(responseDTO.getResult(), List.class);
            bookModels = objectMapper.convertValue(bookModels, objectMapper.getTypeFactory().constructCollectionType(List.class, BookModel.class));
        }
        return bookModels;
    }

    @Override
    public Map<Integer, BigDecimal> getProfitByIds(Integer inventoryId, Set<Integer> ids) {
        ResponseEntity<ResponseDTO> response = bookClient.GetProfitByIds(inventoryId,ids);
        ResponseDTO responseDTO = response.getBody();
        Map<Integer, BigDecimal> map = new HashMap<>();
        if (responseDTO != null && responseDTO.getResult() != null) {
            List<ProfitDTO> profitDTOs = objectMapper.convertValue(
                    responseDTO.getResult(),
                    new TypeReference<List<ProfitDTO>>() {}
            );

            for (ProfitDTO profitDTO : profitDTOs) {
                map.put(profitDTO.getBookId(),profitDTO.getProfit());
            }
        }
        return map;
    }
}
