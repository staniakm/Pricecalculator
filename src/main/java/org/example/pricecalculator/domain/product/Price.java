package org.example.pricecalculator.domain.product;

import java.math.BigDecimal;

public record Price(BigDecimal price, Currency currency) {
}
