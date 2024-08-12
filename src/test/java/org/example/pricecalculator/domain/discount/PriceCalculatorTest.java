package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

public class PriceCalculatorTest {

    @Test
    void shouldReturnTotalPrice() {
        // given
        var priceCalculator = new PriceCalculator();
        var givenPrice = new Price(new BigDecimal("1.00"), Currency.of("PLN"));
        var givenAmount = Amount.of(new BigDecimal("1.00"));
        var expectedTotalPrice = new Price(new BigDecimal("1.00"), Currency.of("PLN"));
        // when
        var result = priceCalculator.calculate(givenPrice, givenAmount);

        // then
        assertThat(result, Matchers.is(expectedTotalPrice));
    }
}
