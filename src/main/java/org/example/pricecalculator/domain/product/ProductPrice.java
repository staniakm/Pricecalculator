package org.example.pricecalculator.domain.product;

import java.util.UUID;

public record ProductPrice(
        UUID productId,
        ProductAvailability productAvalability,
        ProductStatus productStatus
) {
}
