package org.example.pricecalculator.domain.product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAllByIds(List<UUID> ids);
}
