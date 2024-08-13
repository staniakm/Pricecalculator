package org.example.pricecalculator.api;

public record ProductsPricesResponse(
        String productId,
        String discountType,
        String productStatus,
        ProductAvailabilityResponse productAvailability) {
}