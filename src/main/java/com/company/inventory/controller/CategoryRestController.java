package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.ICategoryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryRestController {

    private final ICategoryService categoryService;

    /**
     * GET http://localhost:8080/categories/all
     */
    @GetMapping("/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Categories not found")
    })
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        ResponseEntity<CategoryResponseRest> response = categoryService.search();
        return response;
    }

    /**
     * GET http://localhost:8080/categories/category/1
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = categoryService.searchById(id);
        return response;
    }

    /**
     * POST http://localhost:8080/categories/save
     * @param category
     * @return
     */
    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Category already exists")
    })
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
        // Invalid request check
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            CategoryResponseRest response = new CategoryResponseRest();
            response.setMetadata("ERROR", "-1", "Invalid request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<CategoryResponseRest> response = categoryService.save(category);
        return response;
    }

    /**
     * PUT http://localhost:8080/categories/update/1
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
        // Invalid request check
        if (category == null || id == null || category.getName() == null || category.getName().isEmpty()) {
            CategoryResponseRest response = new CategoryResponseRest();
            response.setMetadata("ERROR", "-1", "Invalid request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<CategoryResponseRest> response = categoryService.update(category, id);
        return response;
    }

    /**
     * DELETE http://localhost:8080/categories/delete/1
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = categoryService.delete(id);
        return response;
    }
}
