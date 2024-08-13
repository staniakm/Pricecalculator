package org.example.pricecalculator.domain.product;

import java.util.UUID;

public record RequestedProductsPrice(
        UUID productId,
        Amount orderedAmount) {
}
