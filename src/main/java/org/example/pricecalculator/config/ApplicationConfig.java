package org.example.pricecalculator.config;

import org.example.pricecalculator.domain.discount.AmountDiscountStrategy;
import org.example.pricecalculator.domain.discount.DiscountDefinition;
import org.example.pricecalculator.domain.discount.NoDiscountStrategy;
import org.example.pricecalculator.domain.discount.PercentageDiscountStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties({
        DiscountDefinitionProperties.class
})
public class ApplicationConfig {

    @Bean
    public AmountDiscountStrategy amountDiscountStrategy(DiscountDefinitionProperties discountDefinitionProperties) {
        return new AmountDiscountStrategy(discountDefinitionProperties.amount);
    }

    @Bean
    public PercentageDiscountStrategy percentageDiscountStrategy(DiscountDefinitionProperties discountDefinitionProperties) {
        return new PercentageDiscountStrategy(discountDefinitionProperties.percentage);
    }

    @Bean
    public NoDiscountStrategy noDiscountStrategy() {
        return new NoDiscountStrategy();
    }
}

@ConfigurationProperties("discount")
class DiscountDefinitionProperties {
    List<DiscountDefinition> percentage;
    List<DiscountDefinition> amount;

    public DiscountDefinitionProperties(List<DiscountDefinition> percentage, List<DiscountDefinition> amount) {
        this.percentage = percentage;
        this.amount = amount;
    }
}