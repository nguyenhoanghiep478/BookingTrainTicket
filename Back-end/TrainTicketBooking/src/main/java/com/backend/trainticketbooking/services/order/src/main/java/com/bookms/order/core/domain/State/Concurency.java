package com.bookms.order.core.domain.State;

public enum Concurency {
    USD("USD"), EUR("EUR"),VND("VND");


    private final String concurency;

    Concurency(String concurency) {
        this.concurency = concurency;
    }

    public String getConcurency() {
        return concurency;
    }
}
