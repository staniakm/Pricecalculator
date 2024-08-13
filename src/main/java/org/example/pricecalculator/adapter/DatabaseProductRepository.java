package org.example.pricecalculator.adapter;

import org.example.pricecalculator.domain.product.Product;
import org.example.pricecalculator.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

//for simplicity in memory map is used
// in normal app, database will be used for fetching data
@Repository
public class DatabaseProductRepository implements ProductRepository {

    private final Map<UUID, Product> products = new HashMap<>();

    @Override
    public Map<UUID, Product> findAllByIds(List<UUID> ids) {
        return products.entrySet()
                .stream()
                .filter(product -> ids.contains(product.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    @Override
    public void save(Product product) {
        products.put(product.id(), product);
    }
}
