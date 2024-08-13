package org.example.pricecalculator.domain.discount;

public interface DiscountStrategy {

    Discount calculate(DiscountContext discountContext);

    DiscountType discountType();
}
