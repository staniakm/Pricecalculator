package org.example.pricecalculator.domain.product;

public record Currency(String currency) {

    public static Currency of(String currency){
        return new Currency(currency);
    }
}
