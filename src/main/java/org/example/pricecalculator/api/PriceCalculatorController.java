package org.example.pricecalculator.api;

import org.example.pricecalculator.domain.discount.DiscountType;
import org.example.pricecalculator.domain.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceCalculatorController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<ProductsPriceCheckResponse> getProductPrices(@RequestBody ProductsPriceCheckRequest productPriceCheckRequest) {
        var result = productService.calculatePrices(toDomain(productPriceCheckRequest.products()), productPriceCheckRequest.discountType());
        var response = mapToResponse(result, productPriceCheckRequest.discountType());
        return ResponseEntity.ok(response);
    }

    private ProductsPriceCheckResponse mapToResponse(List<ProductPrice> result, DiscountType discountType) {
        var products = result.stream()
                .map(product -> new ProductsPricesResponse(product.productId().toString(),
                        discountType.name(),
                        product.productStatus().name(),
                        getProductAvailability(product.productAvalability())))
                .toList();
        return new ProductsPriceCheckResponse(products);
    }

    private ProductAvailabilityResponse getProductAvailability(ProductAvailability productAvailability) {
        if (productAvailability == null) {
            return null;
        } else {
            return new ProductAvailabilityResponse(
                    productAvailability.productUnitPrice().price().toString(),
                    productAvailability.productTotalPrice().price().toString(),
                    productAvailability.discountedPrice().price().toString(),
                    productAvailability.availableAmount().amount().doubleValue(),
                    productAvailability.orderedAmount().amount().doubleValue()
            );
        }
    }

    private List<RequestedProductsPrice> toDomain(List<ProductsRequest> products) {
        return products.stream().map(product -> new RequestedProductsPrice(product.productId(), Amount.of(product.amount()))).toList();
    }


}
