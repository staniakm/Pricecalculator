package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {

    /**
     * Lets make assumption that lowest possible price is 0.01 $
     */
    public static Price calculate(Price unitPrice, Amount amount) {
        var total = unitPrice.price().multiply(amount.amount()).setScale(2, RoundingMode.HALF_UP);
        if (total.equals(new BigDecimal("0.00"))) {
            total = new BigDecimal("0.01");
        }
        return new Price(total, unitPrice.currency());
    }
}
