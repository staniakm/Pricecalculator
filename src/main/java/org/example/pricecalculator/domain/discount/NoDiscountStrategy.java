package org.example.pricecalculator.domain.discount;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public Discount calculate(DiscountContext discountContext) {
        var totalPrice = PriceCalculator.calculate(discountContext.unitPrice(), discountContext.amount());
        return new Discount(
                DiscountType.NO_DISCOUNT,
                totalPrice,
                totalPrice,
                discountContext.amount());
    }
}
