package org.example.pricecalculator.unit.domain.discount;

import org.example.pricecalculator.domain.discount.PriceCalculator;
import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

public class PriceCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "1.00,1.00,1.00",
            "2.00,4.00,8.00",
            "0.01,0.01,0.01", //assumption that lowest possible price is 0.01$
            "10.01,1.01,10.11",
            "10.01,1.01,10.11",
            "10.10,0.33,3.33",
    })
    void shouldReturnTotalPrice(String givenPriceString, String givenAmountString, String expectedTotalPriceString) {
        // given
        var priceCalculator = new PriceCalculator();
        var givenPrice = new Price(new BigDecimal(givenPriceString), Currency.of("PLN"));
        var givenAmount = Amount.of(new BigDecimal(givenAmountString));
        var expectedTotalPrice = new Price(new BigDecimal(expectedTotalPriceString), Currency.of("PLN"));
        // when
        var result = priceCalculator.calculate(givenPrice, givenAmount);

        // then
        assertThat(result, Matchers.is(expectedTotalPrice));
    }
}
