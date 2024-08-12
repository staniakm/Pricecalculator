package org.example.pricecalculator.domain.product;

import java.math.BigDecimal;

public record Price(BigDecimal price, Currency currency) {
    public Price {
        if (price == null) {
            throw new IllegalArgumentException("Price must be provided");
        }
        if (price.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency must be provided");
        }
    }
}
