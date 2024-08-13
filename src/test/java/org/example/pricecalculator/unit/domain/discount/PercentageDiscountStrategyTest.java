package org.example.pricecalculator.unit.domain.discount;

import org.example.pricecalculator.domain.discount.Discount;
import org.example.pricecalculator.domain.discount.DiscountContext;
import org.example.pricecalculator.domain.discount.DiscountDefinition;
import org.example.pricecalculator.domain.discount.PercentageDiscountStrategy;
import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.example.pricecalculator.domain.discount.DiscountType.NO_DISCOUNT;
import static org.example.pricecalculator.domain.discount.DiscountType.PERCENTAGE;
import static org.hamcrest.MatcherAssert.assertThat;

public class PercentageDiscountStrategyTest {

    List<DiscountDefinition> discountDefinitions = List.of(
            new DiscountDefinition(10, new BigDecimal("3.00")),
            new DiscountDefinition(5, new BigDecimal("1.00")),
            new DiscountDefinition(50, new BigDecimal("5.00"))
    );

    @Test
    void shouldReturnPriceWithoutDiscountWhenOrderedItemsAreBelowLimit() {
        // given
        var strategy = new PercentageDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("20.99"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("4"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("83.96"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("83.96"), Currency.of("USD"));
        var expectedDiscount = new Discount(NO_DISCOUNT, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = strategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }


    @Test
    void shouldReturnDiscountBasedOnOrderedAmount() {
        // given
        var discountStrategy = new PercentageDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("20.99"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("21"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("427.57"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("440.79"), Currency.of("USD"));
        var expectedDiscount = new Discount(PERCENTAGE, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }


    @Test
    void shouldReturnDiscountBasedOnOrderedHighAmount() {
        // given
        var discountStrategy = new PercentageDiscountStrategy(discountDefinitions);
        var itemPrice = new Price(new BigDecimal("0.99"), Currency.of("USD"));
        var itemsOrdered = Amount.of(new BigDecimal("10000"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var discountPrice = new Price(new BigDecimal("9405.00"), Currency.of("USD"));
        var totalPrice = new Price(new BigDecimal("9900.00"), Currency.of("USD"));
        var expectedDiscount = new Discount(PERCENTAGE, discountPrice, totalPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }

}
