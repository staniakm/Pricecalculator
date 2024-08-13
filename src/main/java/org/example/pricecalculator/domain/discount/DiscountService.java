package org.example.pricecalculator.domain.discount;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountService {

    private final Map<DiscountType, DiscountStrategy> strategies;

    public DiscountService(List<DiscountStrategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(DiscountStrategy::discountType, Function.identity()));
    }
}
