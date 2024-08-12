package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.example.pricecalculator.domain.discount.DiscountType.AMOUNT;
import static org.hamcrest.MatcherAssert.assertThat;

public class AmountBasedStrategyTest {

    @Test
    void shouldReturnDiscountBasedOnOrderedAmount() {
        // given
        var discountStrategy = new AmountBasedStrategy();
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
}
