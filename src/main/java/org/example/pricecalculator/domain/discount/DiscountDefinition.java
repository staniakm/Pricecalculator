package org.example.pricecalculator.domain.discount;

import java.math.BigDecimal;

public record DiscountDefinition(Integer limit, BigDecimal discount) {
}
