package org.example.pricecalculator.domain.product;

import java.util.UUID;

public record Product(UUID id, Price unitPrice, Amount availableAmount) {
}
