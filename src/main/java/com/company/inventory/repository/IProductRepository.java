package com.company.inventory.repository;

import com.company.inventory.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE :name")
    List<Product> findByNameLike(String name);

    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
}
