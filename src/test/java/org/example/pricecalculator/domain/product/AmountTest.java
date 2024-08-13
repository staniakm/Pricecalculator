package org.example.pricecalculator.domain.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AmountTest {

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {

        var throwed = assertThrows(IllegalArgumentException.class, () -> new Amount(null));

        assertEquals("Amount should be provided", throwed.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        var throwed = assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("0")));

        assertEquals("Amount should be greater than zero", throwed.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsBelowZero() {
        var throwed = assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("-0.01")));

        assertEquals("Amount should be greater than zero", throwed.getMessage());
    }

    @Test
    void shouldCreateValidAmount() {
        var amount = assertDoesNotThrow(() -> new Amount(new BigDecimal("0.01")));

        assertNotNull(amount);
    }
}
