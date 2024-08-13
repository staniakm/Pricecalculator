package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AmountDiscountStrategy implements DiscountStrategy {
    private final List<DiscountDefinition> discounts;

    public AmountDiscountStrategy(List<DiscountDefinition> discounts) {
        this.discounts = discounts.stream().sorted((d1, d2) -> Integer.compare(d2.minLimit(), d1.minLimit())).toList();
    }

    /**
     * Lets assume that lowest possible price is 0.01
     * When discounted price hit zero or lower 0.01 will be returned
     */
    @Override
    public Discount calculate(DiscountContext discountContext) {
        var totalPrice = PriceCalculator.calculate(discountContext.unitPrice(), discountContext.amount());
        var discountDefinition = findDiscount(discountContext.amount());
        return discountDefinition
                .map(discount -> calculatePrice(totalPrice, discount))
                .map(price -> buildDiscount(price, totalPrice, discountContext.amount()))
                .orElse(new Discount(
                        DiscountType.NO_DISCOUNT,
                        totalPrice,
                        totalPrice,
                        discountContext.amount()));

    }

    private Discount buildDiscount(Price discountedPrice, Price totalPrice, Amount amount) {
        return new Discount(
                DiscountType.AMOUNT,
                discountedPrice,
                totalPrice,
                amount);
    }

    private Price calculatePrice(Price totalPrice, DiscountDefinition discount) {
        var discounted = totalPrice.price().subtract(discount.discount());
        if (discounted.compareTo(BigDecimal.ZERO) <= 0) {
            return new Price(new BigDecimal("0.01"), totalPrice.currency());
        } else {
            return new Price(discounted, totalPrice.currency());
        }
    }

    private Optional<DiscountDefinition> findDiscount(Amount orderedItemsCount) {
        return discounts.stream()
                .filter(discount -> new BigDecimal(discount.minLimit()).compareTo(orderedItemsCount.amount()) <= 0)
                .findFirst();
    }
}
