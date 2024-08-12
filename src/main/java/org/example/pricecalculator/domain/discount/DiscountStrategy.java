package org.example.pricecalculator.domain.discount;

public interface DiscountStrategy {

    public Discount calculate(DiscountContext discountContext);
}
