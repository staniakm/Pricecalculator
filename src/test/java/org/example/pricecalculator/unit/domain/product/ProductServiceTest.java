package org.example.pricecalculator.unit.domain.product;

import org.example.pricecalculator.domain.discount.*;
import org.example.pricecalculator.domain.product.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    List<DiscountStrategy> strategies = List.of(
            new NoDiscountStrategy(),
            new AmountDiscountStrategy(List.of(
                    new DiscountDefinition(10, new BigDecimal("2.00")),
                    new DiscountDefinition(5, new BigDecimal("1.00")),
                    new DiscountDefinition(100, new BigDecimal("5.00"))
            ))
    );
    DiscountService discountService = new DiscountService(strategies, new NoDiscountStrategy());

    Currency USD = Currency.of("UDS");


    @Test
    void shouldHandleNotExistingProductsInRepository() {
        // given
        Mockito.when(productRepository.findAllByIds(Mockito.anyList())).thenReturn(Map.of());
        var product1Id = UUID.randomUUID();
        var product2Id = UUID.randomUUID();
        var requestedProducts = List.of(
                new RequestedProductsPrice(product1Id, Amount.of(new BigDecimal(10))),
                new RequestedProductsPrice(product2Id, Amount.of(new BigDecimal(10)))
        );
        var expectedResult = List.of(
                new ProductPrice(product1Id, null, ProductStatus.NOT_EXISTS),
                new ProductPrice(product2Id, null, ProductStatus.NOT_EXISTS)
        );
        var productService = new ProductService(productRepository, discountService);


        // when
        var result = productService.calculatePrices(requestedProducts, DiscountType.NO_DISCOUNT);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldHandleExistingProductsInRepository() {
        // given
        var product1Id = UUID.randomUUID();
        Mockito.when(productRepository.findAllByIds(Mockito.anyList()))
                .thenReturn(Map.of(
                                product1Id, new Product(product1Id, new Price(new BigDecimal("10.00"), USD), Amount.of(new BigDecimal("100.00")))
                        )
                );
        var requestedProducts = List.of(
                new RequestedProductsPrice(product1Id, Amount.of(new BigDecimal("10.00")))
        );
        var availableProduct = new ProductPrice(product1Id, new ProductAvailability(
                new Price(new BigDecimal("10.00"), USD),
                new Price(new BigDecimal("100.00"), USD),
                new Price(new BigDecimal("98.00"), USD),
                Amount.of(new BigDecimal("10.00")),
                Amount.of(new BigDecimal("100.00"))
        ), ProductStatus.AVAILABLE);
        var expectedResult = List.of(availableProduct);
        var productService = new ProductService(productRepository, discountService);

        // when
        var result = productService.calculatePrices(requestedProducts, DiscountType.AMOUNT);

        // then
        assertEquals(expectedResult, result);
    }


    @Test
    void shouldHandleExistingProductsWithNotEnoughAmountInRepository() {
        // given
        var product2Id = UUID.randomUUID();
        Mockito.when(productRepository.findAllByIds(Mockito.anyList()))
                .thenReturn(Map.of(
                                product2Id, new Product(product2Id, new Price(new BigDecimal("10.00"), USD), Amount.of(new BigDecimal("20.00"))) // not enough amount
                        )
                );
        var requestedProducts = List.of(
                new RequestedProductsPrice(product2Id, Amount.of(new BigDecimal("100.00")))
        );
        var notEnoughProduct = new ProductPrice(product2Id, new ProductAvailability(
                new Price(new BigDecimal("10.00"), USD),
                new Price(new BigDecimal("200.00"), USD),
                new Price(new BigDecimal("198.00"), USD),
                Amount.of(new BigDecimal("100.00")),
                Amount.of(new BigDecimal("20.00"))
        ), ProductStatus.NOT_ENOUGH);
        var expectedResult = List.of(notEnoughProduct);
        var productService = new ProductService(productRepository, discountService);

        // when
        var result = productService.calculatePrices(requestedProducts, DiscountType.AMOUNT);

        // then
        assertEquals(expectedResult, result);
    }
}
