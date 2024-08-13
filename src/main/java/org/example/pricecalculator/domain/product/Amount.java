package org.example.pricecalculator.domain.product;

import java.math.BigDecimal;

public record Amount(BigDecimal amount) {

    public Amount {
        if (amount == null) {
            throw new IllegalArgumentException("Amount should be provided");
        }
    }

    public static Amount of(BigDecimal amount) {
        return new Amount(amount);
    }
}
