package org.example.pricecalculator.domain.discount;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class DiscountCalculatorTest {
    @Test
    void shouldReturnDiscountWhenNoDiscountStrategySelected() {
        // given
        var discountStrategy = new NoDiscountStrategy();
        var discountContext = new DiscountContext();
        var expectedDiscount = new Discount(DiscountType.NO_DISCOUNT);

        // when
        var discount = discountStrategy.calculate(discountContext);

        // then
        assertThat(discount, Matchers.is(expectedDiscount));
    }
}
