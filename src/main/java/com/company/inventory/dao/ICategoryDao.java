package com.company.inventory.dao;

import com.company.inventory.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
