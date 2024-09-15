package com.booksms.marketing.interfaceLayer.dto.request;

import com.booksms.marketing.application.model.BookModel;
import com.booksms.marketing.application.model.OrderItemModel;
import com.booksms.marketing.application.model.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String attachment;
    private String recipient;
    private String message;
    private Long orderNumber;
    private Status status;
    private int customerId;
    private BigDecimal totalPrice;
    private List<OrderItemModel> orderItems;
    private String paymentMethod;
    private List<BookModel> bookModels;
    private Integer paymentId;
    private boolean isVerified;

}
