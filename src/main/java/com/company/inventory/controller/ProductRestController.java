package com.company.inventory.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.Product;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.service.IProductService;
import com.company.inventory.util.Image;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("products")
public class ProductRestController {
    private final IProductService iProductService;

    ProductRestController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    /**
     * Save a product
     * 
     * @param image
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @return ResponseEntity<ProductResponseRest>
     * @throws IOException
     */
    @PostMapping()
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("price") Integer price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("categoryId") Long categoryId) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setImage(Image.compressZLib(image.getBytes()));

        return iProductService.save(product, categoryId);

    }

    /**
     * Find product by id
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseRest> findById(@PathVariable Long id) {
        return iProductService.findById(id);
    }

    // find product by name
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponseRest> findByNameLike(@PathVariable String name) {
        return iProductService.findByNameLike(name);
    }

    // find all products
    @GetMapping()
    public ResponseEntity<ProductResponseRest> findAll() {
        return iProductService.findAll();
    }

    // delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseRest> delete(@PathVariable Long id) {
        return iProductService.delete(id);
    }

    /**
     * Update a product
     * 
     * @param image
     * @param id
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @return ResponseEntity<ProductResponseRest>
     * @throws IOException
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<ProductResponseRest> update(
            @RequestParam("image") MultipartFile image,
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Integer price,
            @RequestParam("quantity") Integer quantity,
            @PathVariable Long categoryId) {

        if (image.isEmpty() || id == null || name == null || name.isEmpty() || price == null || quantity == null
                || categoryId == null) {
            ProductResponseRest response = new ProductResponseRest();
            response.setMetadata("ERROR", "-1", "Invalid request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setImage(Image.compressZLib(image.getBytes()));

            return iProductService.update(product, categoryId);
        } catch (IOException e) {
            // Log the exception, if you have a logger
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
