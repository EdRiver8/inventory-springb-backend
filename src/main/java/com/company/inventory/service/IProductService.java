package com.company.inventory.service;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);

    ResponseEntity<ProductResponseRest> update(Product product, Long categoryId);

    ResponseEntity<ProductResponseRest> delete(Long id);

    ResponseEntity<ProductResponseRest> findAll();

    ResponseEntity<ProductResponseRest> findById(Long id);

    ResponseEntity<ProductResponseRest> findByNameLike(String name);
}
