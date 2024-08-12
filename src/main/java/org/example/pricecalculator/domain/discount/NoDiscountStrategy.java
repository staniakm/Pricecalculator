package org.example.pricecalculator.domain.discount;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public Discount calculate(DiscountContext discountContext) {
        return new Discount(
                DiscountType.NO_DISCOUNT,
                discountContext.unitPrice(),
                discountContext.unitPrice(),
                discountContext.amount());
    }
}
