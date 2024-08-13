package org.example.pricecalculator.integration.api;

public record ProductPriceCheckTestResponse (String productId, String discountType, String productStatus, ProductAvailabilityTestResponse productAvailability){}