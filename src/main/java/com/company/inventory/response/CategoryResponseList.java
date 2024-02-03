package com.company.inventory.response;

import com.company.inventory.model.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseList {
    private List<Category> categories = new ArrayList<>();

    // Initialize the list of categories
    // public CategoryResponseList() {
    // this.categories = new ArrayList<>();
    // }
}