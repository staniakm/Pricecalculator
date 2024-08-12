package org.example.pricecalculator.domain.product;

import java.math.BigDecimal;

public record Amount(BigDecimal amount) {
    public static Amount of(BigDecimal amount) {
        return new Amount(amount);
    }
}
