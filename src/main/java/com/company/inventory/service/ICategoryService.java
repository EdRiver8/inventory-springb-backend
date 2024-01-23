package com.company.inventory.service;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<CategoryResponseRest> search();
    ResponseEntity<CategoryResponseRest> searchById(Long id);
    ResponseEntity<CategoryResponseRest> save(Category category);
    ResponseEntity<CategoryResponseRest> update(Category category, Long id);
    ResponseEntity<CategoryResponseRest> delete(Long id);

}
