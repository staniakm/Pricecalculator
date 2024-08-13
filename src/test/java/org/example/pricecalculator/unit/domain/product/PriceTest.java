package org.example.pricecalculator.unit.domain.product;

import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PriceTest {

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        // expected
        var thrown = assertThrows(IllegalArgumentException.class, () -> new Price(null, Currency.of("PLN")));

        assertEquals("Price must be provided", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsZero() {
        // expected
        var thrown = assertThrows(IllegalArgumentException.class, () -> new Price(new BigDecimal("0.00"), Currency.of("PLN")));

        assertEquals("Price must be greater than zero", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNotProvided() {
        // expected
        var thrown = assertThrows(IllegalArgumentException.class, () -> new Price(new BigDecimal("0.01"), null));

        assertEquals("Currency must be provided", thrown.getMessage());
    }

    @Test
    void shouldBuildPriceObject() {
        // expected
        var price = new Price(new BigDecimal("0.01"), new Currency("PLN"));

        assertNotNull(price);
    }
}
