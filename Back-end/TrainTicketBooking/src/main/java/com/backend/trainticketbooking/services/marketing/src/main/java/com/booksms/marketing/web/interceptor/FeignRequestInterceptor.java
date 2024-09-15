package com.booksms.marketing.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("X-SERVICE-REQUEST","MARKETING-SERVICE");
    }
}
