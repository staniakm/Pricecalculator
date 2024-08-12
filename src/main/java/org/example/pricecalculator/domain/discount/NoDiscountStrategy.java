package org.example.pricecalculator.domain.discount;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public Discount calculate(DiscountContext discountContext) {
        return new Discount();
    }
}
