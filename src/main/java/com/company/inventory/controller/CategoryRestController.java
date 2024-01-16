package com.company.inventory.controller;

import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryRestController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return categoryService.search();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = categoryService.searchById(id);
        return response;
    }
}
