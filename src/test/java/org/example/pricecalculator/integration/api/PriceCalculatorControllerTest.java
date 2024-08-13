package org.example.pricecalculator.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pricecalculator.BaseIntegration;
import org.example.pricecalculator.api.ProductsPriceCheckRequest;
import org.example.pricecalculator.api.ProductsRequest;
import org.example.pricecalculator.domain.discount.DiscountType;
import org.example.pricecalculator.domain.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PriceCalculatorControllerTest extends BaseIntegration {

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.save(new Product(UUID.fromString("60e01266-a6de-484e-b3c0-325ae19575e7"),
                new Price(new BigDecimal("100.00"), Currency.of("USD")), Amount.of(101.1)));
    }

    @Test
    public void shouldReturnOkResponse() throws Exception {
        // expected
        var request = new ProductsPriceCheckRequest(DiscountType.AMOUNT, List.of(new ProductsRequest(UUID.randomUUID(), 10.01)));
        var content = om.writeValueAsString(request);
        mockMvc
                .perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWithResponseBody() throws Exception {
        // expected
        var productId = UUID.randomUUID();
        var request = new ProductsPriceCheckRequest(DiscountType.AMOUNT, List.of(new ProductsRequest(productId, 10.01)));
        var content = om.writeValueAsString(request);
        var result = mockMvc
                .perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        var responseBody = result.andReturn().getResponse().getContentAsString();
        var response = om.readValue(responseBody, ProductsPriceCheckTestResponse.class);

        assertNotNull(response);
    }

    @Test
    public void shouldReturnOkWithResponseBodyAndProduct() throws Exception {
        // expected
        var productId = UUID.fromString("60e01266-a6de-484e-b3c0-325ae19575e7");
        var request = new ProductsPriceCheckRequest(DiscountType.AMOUNT, List.of(new ProductsRequest(productId, 10.01)));
        var content = om.writeValueAsString(request);
        var result = mockMvc
                .perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        var responseBody = result.andReturn().getResponse().getContentAsString();
        var response = om.readValue(responseBody, ProductsPriceCheckTestResponse.class);

        assertEquals(1, response.products().size());
        var firstProduct = response.products().getFirst();
        assertEquals(productId.toString(), firstProduct.productId());
        assertEquals(DiscountType.AMOUNT.toString(), firstProduct.discountType());
        assertEquals(ProductStatus.AVAILABLE.toString(), firstProduct.productStatus());
        assertEquals("100.00", firstProduct.productAvailability().unitPrice());
        assertEquals("1001.00", firstProduct.productAvailability().totalPrice());
        assertEquals("999.00", firstProduct.productAvailability().discountedTotalPrice());
        assertEquals(101.1, firstProduct.productAvailability().availableAmount());
        assertEquals(10.01, firstProduct.productAvailability().orderedAmount());
    }
}
