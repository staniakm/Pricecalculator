package org.example.pricecalculator.domain.discount;

import org.example.pricecalculator.domain.product.Amount;
import org.example.pricecalculator.domain.product.Price;

public record Discount(
        DiscountType discountType,
        Price discountedTotalPrice,
        Price originalTotalPrice,
        Price unitPrice,
        Amount itemsOrdered) {
}
