package org.example.pricecalculator.adapter;

import org.example.pricecalculator.domain.product.ProductRepository;
import org.example.pricecalculator.domain.product.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class DatabaseProductRepository implements ProductRepository {

    private final Map<UUID, Product> products = new HashMap<>();


    @Override
    public List<Product> findAllByIds(List<UUID> ids) {
        return products.entrySet()
                .stream()
                .filter(product -> ids.contains(product.getKey()))
                .map(Map.Entry::getValue)
                .toList();
    }
}
