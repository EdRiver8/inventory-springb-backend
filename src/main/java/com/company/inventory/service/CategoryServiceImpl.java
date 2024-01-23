package com.company.inventory.service;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseList;
import com.company.inventory.response.CategoryResponseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao categoryDao;

    @Override
    //this method will not modify the DB. It's a hint for persistence provider can be used for performance optimizations
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        CategoryResponseList categoryResponseList = new CategoryResponseList(); // Initialize the CategoryResponseList
        response.setCategoryResponseList(categoryResponseList); // Set the initialized CategoryResponseList to the response
        try {
            List<Category> categories = categoryDao.findAll();
            if (categories != null) { // Check if categories is not null
                response.getCategoryResponseList().setCategories(categories);
                response.setMetadata("SUCCESS", "00", "Search categories OK");
            } else {
                response.setMetadata("ERROR", "-1", "No categories found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Search categories ERROR");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        CategoryResponseList categoryResponseList = new CategoryResponseList();
        response.setCategoryResponseList(categoryResponseList);
        try {
            if (categoryDao.findById(id).isPresent()) {
                response.getCategoryResponseList().setCategories(categoryDao.findById(id).stream().toList());
                response.setMetadata("SUCCESS", "00", "Search category OK");
            }else {
                response.setMetadata("ERROR", "-1", "Category not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Search category ERROR");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        CategoryResponseList categoryResponseList = new CategoryResponseList();
        response.setCategoryResponseList(categoryResponseList);
        try {
            if (categoryDao.findByName(category.getName()).isEmpty()) {
                Category categorySaved = categoryDao.save(category);
                response.getCategoryResponseList().getCategories().add(categorySaved);
                response.setMetadata("SUCCESS", "00", "Category saved OK");
            }else {
                response.setMetadata("ERROR", "-1", "Category already exists");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Category not saved");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        CategoryResponseList categoryResponseList = new CategoryResponseList();
        response.setCategoryResponseList(categoryResponseList);
        try {
            if (categoryDao.findById(id).isPresent()) {
                Category categoryFound = categoryDao.findById(id).get();
                categoryFound.setName(category.getName());
                categoryFound.setDescription(category.getDescription());

                Category categoryUpdated = categoryDao.save(categoryFound);
                response.getCategoryResponseList().getCategories().add(categoryUpdated);
                response.setMetadata("SUCCESS", "00", "Category updated OK");
            }else {
                response.setMetadata("ERROR", "-1", "Category not updated");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Category not updated");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponseRest> delete(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        CategoryResponseList categoryResponseList = new CategoryResponseList();
        response.setCategoryResponseList(categoryResponseList);
        try {
            if (categoryDao.findById(id).isPresent()) {
                response.getCategoryResponseList().getCategories().add(categoryDao.findById(id).get());
                categoryDao.deleteById(id);
                response.setMetadata("SUCCESS", "00", "Category deleted OK");
            }else {
                response.setMetadata("ERROR", "-1", "Category not deleted");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Category not deleted");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}