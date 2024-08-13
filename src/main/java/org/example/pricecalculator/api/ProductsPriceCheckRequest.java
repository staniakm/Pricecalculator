package org.example.pricecalculator.api;

import org.example.pricecalculator.domain.discount.DiscountType;

import java.util.List;

public record ProductsPriceCheckRequest(DiscountType discountType, List<ProductsRequest> products){}