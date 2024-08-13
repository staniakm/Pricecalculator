package org.example.pricecalculator.config;


import org.example.pricecalculator.domain.product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Profile("local")
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private final ProductRepository productRepository;


    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Initialize initial data");
        productRepository.save(new Product(UUID.fromString("794c5988-4bbb-4f6b-9988-0d40e15dd8f6"), new Price(new BigDecimal("9.99"), Currency.of("USD")), Amount.of(new BigDecimal("100.00"))));
        productRepository.save(new Product(UUID.fromString("60e01266-a6de-484e-b3c0-325ae19575e6"), new Price(new BigDecimal("19.99"), Currency.of("USD")), Amount.of(new BigDecimal("10.00"))));
        productRepository.save(new Product(UUID.fromString("6ba4b7de-fb5c-4101-a02b-9d4b6dc79f19"), new Price(new BigDecimal("109.80"), Currency.of("USD")), Amount.of(new BigDecimal("50.00"))));
        productRepository.save(new Product(UUID.fromString("161fda06-205d-45d2-8234-04cf654b43b2"), new Price(new BigDecimal("0.99"), Currency.of("USD")), Amount.of(new BigDecimal("1000.00"))));
    }
}
