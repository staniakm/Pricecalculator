package org.example.pricecalculator.domain.product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductRepository {
    Map<UUID, Product> findAllByIds(List<UUID> ids);

    void save(Product product);
}
