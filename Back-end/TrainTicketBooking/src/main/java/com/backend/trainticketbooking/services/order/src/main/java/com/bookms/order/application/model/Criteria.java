package com.bookms.order.application.model;

import lombok.*;

@Getter
@Setter
@Builder
public class Criteria {
    private String key;
    private String operator;
    private Object value;
    private String sql;
}
