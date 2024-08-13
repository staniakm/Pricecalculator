package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class PercentageDiscountStrategy implements DiscountStrategy {

    private final List<DiscountDefinition> discounts;

    public PercentageDiscountStrategy(List<DiscountDefinition> discounts) {
        this.discounts = discounts.stream().sorted((d1, d2) -> Integer.compare(d2.minLimit(), d1.minLimit())).toList();
    }

    @Override
    public Discount calculate(DiscountContext discountContext) {
        var totalPrice = PriceCalculator.calculate(discountContext.unitPrice(), discountContext.amount());
        var discountDefinition = findDiscount(discountContext.amount());
        return discountDefinition
                .map(discountPercentage -> applyDiscount(totalPrice, discountPercentage))
                .map(price -> buildDiscount(price, totalPrice, discountContext.amount()))
                .orElse(new Discount(
                        DiscountType.NO_DISCOUNT,
                        totalPrice,
                        totalPrice,
                        discountContext.amount()));
    }

    private Discount buildDiscount(Price discountedPrice, Price totalPrice, Amount amount) {
        return new Discount(
                DiscountType.PERCENTAGE,
                discountedPrice,
                totalPrice,
                amount);
    }

    private Price applyDiscount(Price totalPrice, BigDecimal discount) {
        var discountedValue = totalPrice.price().multiply(discount).setScale(2, RoundingMode.HALF_UP);

        var discountedPrice = totalPrice.price().subtract(discountedValue);
        return new Price(discountedPrice, totalPrice.currency());
    }

    private Optional<BigDecimal> findDiscount(Amount orderedItemsCount) {
        return discounts.stream()
                .filter(discount -> new BigDecimal(discount.minLimit()).compareTo(orderedItemsCount.amount()) <= 0)
                .map(discount -> discount.discount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))
                .findFirst();
    }
}
