package org.example.pricecalculator.api;

import java.util.List;

public record ProductsPriceCheckResponse(List<ProductsPricesResponse> products){}
