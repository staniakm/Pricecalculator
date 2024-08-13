package org.example.pricecalculator.domain.discount;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountService {

    private final Map<DiscountType, DiscountStrategy> strategies;
    private final DiscountStrategy defaultStrategy;

    public DiscountService(List<DiscountStrategy> strategies, DiscountStrategy defaultStrategy) {
        this.strategies = strategies.stream().collect(Collectors.toMap(DiscountStrategy::discountType, Function.identity()));
        this.defaultStrategy = defaultStrategy;
    }

    public Discount calculateDiscount(DiscountType discountType, DiscountContext discountContext) {
        return strategies.getOrDefault(discountType, defaultStrategy)
                .calculate(discountContext);
    }
}
