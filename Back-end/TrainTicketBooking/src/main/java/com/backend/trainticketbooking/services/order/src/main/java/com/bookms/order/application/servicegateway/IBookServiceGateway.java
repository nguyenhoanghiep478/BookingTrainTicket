package com.bookms.order.application.servicegateway;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.UpdateQuantityModel;
import com.bookms.order.interfaceLayer.DTO.ProfitDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBookServiceGateway {
    BookModel updateQuantity(UpdateQuantityModel updateQuantityModel);

    List<BookModel> findAllBookWithListId(Set<Integer> booksId);

    Map<Integer, BigDecimal> getProfitByIds(Integer inventoryId, Set<Integer> ids);
}
