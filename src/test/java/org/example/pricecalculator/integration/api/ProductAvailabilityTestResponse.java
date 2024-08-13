package org.example.pricecalculator.integration.api;

public record ProductAvailabilityTestResponse(
        String unitPrice,
        String totalPrice,
        String discountedTotalPrice,
        double availableAmount,
        double orderedAmount) {
}
