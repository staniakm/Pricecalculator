package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.example.pricecalculator.domain.discount.DiscountType.NO_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;

public class DiscountCalculatorTest {
    @Test
    void shouldReturnDiscountWhenNoDiscountStrategySelected() {
        // given
        var discountStrategy = new NoDiscountStrategy();
        var itemPrice = new Price(new BigDecimal("20.99"), Currency.of("PLN"));
        var itemsOrdered = Amount.of(new BigDecimal("21"));
        var discountContext = new DiscountContext(itemPrice, itemsOrdered);
        var expectedDiscount = new Discount(NO_DISCOUNT, itemPrice, itemPrice, itemsOrdered);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }
}
