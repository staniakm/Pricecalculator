package org.example.pricecalculator.domain.product;

public record ProductAvailability(Price productUnitPrice, Price productTotalPrice, Price discountedPrice,
                                  Amount orderedAmount, Amount availableAmount) {
}
