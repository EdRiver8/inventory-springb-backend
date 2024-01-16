package com.company.inventory.service;

import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<CategoryResponseRest> search();
    ResponseEntity<CategoryResponseRest> searchById(Long id);
}