package org.example.pricecalculator.domain.product;

import org.example.pricecalculator.domain.discount.DiscountContext;
import org.example.pricecalculator.domain.discount.DiscountService;
import org.example.pricecalculator.domain.discount.DiscountType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountService discountService;

    public ProductService(ProductRepository productRepository, DiscountService discountService) {
        this.productRepository = productRepository;
        this.discountService = discountService;
    }

    public List<ProductPrice> calculatePrices(List<RequestedProductsPrice> products, DiscountType discountType) {
        var existingProducts = productRepository
                .findAllByIds(products.stream().map(RequestedProductsPrice::productId).toList());

        return products
                .stream()
                .map(requestedProduct -> calculateProductPrice(discountType, requestedProduct, existingProducts))
                .toList();

    }

    // assumption: If we dont have enough product amount we will calculate discount for max possible amount
    // should be discussed with business
    // another possibility is just return NOT_ENOUGH status without any discount
    private ProductPrice calculateProductPrice(DiscountType discountType, RequestedProductsPrice requestedProductsPrice, Map<UUID, Product> existingProducts) {
        Product product = existingProducts.get(requestedProductsPrice.productId());

        ProductAvailability productAvailability = getProductAvailability(product, requestedProductsPrice, discountType);

        if (productAvailability == null) {
            return new ProductPrice(requestedProductsPrice.productId(), null, ProductStatus.NOT_EXISTS);
        } else {
            return new ProductPrice(requestedProductsPrice.productId(), productAvailability, calculateStatus(requestedProductsPrice, product));
        }
    }

    private static ProductStatus calculateStatus(RequestedProductsPrice requestedProductsPrice, Product product) {
        return product.availableAmount().amount().compareTo(requestedProductsPrice.orderedAmount().amount()) < 0 ? ProductStatus.NOT_ENOUGH : ProductStatus.AVAILABLE;
    }

    private ProductAvailability getProductAvailability(Product product, RequestedProductsPrice requestedProductsPrice, DiscountType discountType) {
        if (product == null) {
            return null;
        } else {
            var amount = requestedProductsPrice.orderedAmount().amount().compareTo(product.availableAmount().amount()) < 0
                    ? requestedProductsPrice.orderedAmount()
                    : product.availableAmount();
            var context = new DiscountContext(product.unitPrice(), amount);
            var discount = discountService.calculateDiscount(discountType, context);
            return new ProductAvailability(
                    discount.unitPrice(),
                    discount.originalTotalPrice(),
                    discount.discountedTotalPrice(),
                    requestedProductsPrice.orderedAmount(),
                    product.availableAmount());
        }
    }
}
