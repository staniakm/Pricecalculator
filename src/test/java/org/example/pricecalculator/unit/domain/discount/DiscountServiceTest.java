package org.example.pricecalculator.unit.domain.discount;

import org.example.pricecalculator.domain.discount.*;
import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Currency;
import org.example.pricecalculator.domain.product.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DiscountServiceTest {

    List<DiscountStrategy> strategies = List.of(
            new NoDiscountStrategy(),
            new AmountDiscountStrategy(List.of(
                    new DiscountDefinition(10, new BigDecimal("2.00")),
                    new DiscountDefinition(5, new BigDecimal("1.00")),
                    new DiscountDefinition(100, new BigDecimal("5.00"))
            ))
    );

    @Test
    void shouldReturnDiscount() {
        //given
        var service = new DiscountService(strategies, new NoDiscountStrategy());
        var discountContext = new DiscountContext(new Price(new BigDecimal("9.99"), Currency.of("USD")), Amount.of(BigDecimal.valueOf(2)));

        // when
        var result = service.calculateDiscount(DiscountType.NO_DISCOUNT, discountContext);

        // then
        assertNotNull(result);
    }

    @Test
    void shouldReturnDefaultDiscountWhenDiscountTypeWasNotFound() {
        //given
        var service = new DiscountService(strategies, new NoDiscountStrategy());
        var discountContext = new DiscountContext(new Price(new BigDecimal("9.99"), Currency.of("USD")), Amount.of(BigDecimal.valueOf(2)));

        // when
        var result = service.calculateDiscount(DiscountType.PERCENTAGE, discountContext);

        // then
        assertNotNull(result);
        assertEquals(DiscountType.NO_DISCOUNT, result.discountType());
    }


    @Test
    void shouldReturnDiscountOfSelectedType() {
        //given
        var service = new DiscountService(strategies, new NoDiscountStrategy());
        var discountContext = new DiscountContext(new Price(new BigDecimal("9.99"), Currency.of("USD")), Amount.of(BigDecimal.valueOf(10)));

        // when
        var result = service.calculateDiscount(DiscountType.AMOUNT, discountContext);

        // then
        assertNotNull(result);
        assertEquals(DiscountType.AMOUNT, result.discountType());
    }

}
