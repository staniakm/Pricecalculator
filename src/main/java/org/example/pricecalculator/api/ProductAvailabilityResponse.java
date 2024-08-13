package org.example.pricecalculator.api;

public record ProductAvailabilityResponse(String unitPrice,
                                          String totalPrice,
                                          String discountedTotalPrice,
                                          double availableAmount,
                                          double orderedAmount) {
}
