package com.company.inventory.response;

import com.company.inventory.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseList {
    private List<Product> products;

    // Initialize the list of products
    public ProductResponseList() {
        this.products = new ArrayList<>();
    }
}
