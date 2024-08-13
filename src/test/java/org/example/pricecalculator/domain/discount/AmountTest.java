package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
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
    void shouldNotThrowExceptionWhenAmountIsZero() {
        var amount = assertDoesNotThrow(() -> new Amount(new BigDecimal("0")));

        assertNotNull(amount);
    }
}
