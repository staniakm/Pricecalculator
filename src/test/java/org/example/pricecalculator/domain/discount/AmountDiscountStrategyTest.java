package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.example.pricecalculator.domain.discount.DiscountType.AMOUNT;
import static org.example.pricecalculator.domain.discount.DiscountType.NO_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;

public class AmountDiscountStrategyTest {


    List<DiscountDefinition> discountDefinitions = List.of(
            new DiscountDefinition(10, new BigDecimal("2.00")),
            new DiscountDefinition(5, new BigDecimal("1.00")),
            new DiscountDefinition(100, new BigDecimal("5.00"))
    );

    @Test
    void shouldReturnNoDiscountPriceEqualToTotalWhenNoLimitWasReached() {
        // given
        var discountStrategy = new AmountDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("20.99"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("4"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("83.96"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("83.96"), Currency.of("USD"));
        var expectedDiscount = new Discount(NO_DISCOUNT, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }

    @Test
    void shouldReturnDiscountBasedOnOrderedAmount() {
        // given
        var discountStrategy = new AmountDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("20.99"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("21"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("438.79"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("440.79"), Currency.of("USD"));
        var expectedDiscount = new Discount(AMOUNT, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }

    @Test
    void shouldReturnLowestPossiblePriceWhenDiscountedPriceIsZero() {
        // given
        var discountStrategy = new AmountDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("0.2"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("10"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("0.01"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("2.00"), Currency.of("USD"));
        var expectedDiscount = new Discount(AMOUNT, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }

    @Test
    void shouldReturnLowestPossiblePriceWhenDiscountedPriceBelowZero() {
        // given
        var discountStrategy = new AmountDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("0.1"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("10"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("0.01"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("1.00"), Currency.of("USD"));
        var expectedDiscount = new Discount(AMOUNT, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }
}
