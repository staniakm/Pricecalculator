package org.example.pricecalculator.api;

import java.util.UUID;

public record ProductsRequest(UUID productId, double amount) {
}
