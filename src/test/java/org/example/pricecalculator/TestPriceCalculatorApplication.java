package org.example.pricecalculator;

import org.springframework.boot.SpringApplication;

public class TestPriceCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.from(PriceCalculatorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
