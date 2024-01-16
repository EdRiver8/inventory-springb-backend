package com.company.inventory.service;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao categoryDao;

    @Override
    //this method will not modify the DB. It's a hint for persistence provider can be used for performance optimizations
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> categories = categoryDao.findAll();
            response.getCategoryResponseList().setCategories(categories);
            response.setMetadata("SUCCESS", "00", "Search categories OK");
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Search categories ERROR");
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> categories = null;
        try {
            Optional<Category> category = categoryDao.findById(id);
            if (category.isPresent()) {
                categories.add(category.get());
                response.getCategoryResponseList().setCategories(categories);
                response.setMetadata("SUCCESS", "00", "Search category OK");
            }else {
                response.setMetadata("ERROR", "-1", "Category not found");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("ERROR", "-1", "Search category ERROR");
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
