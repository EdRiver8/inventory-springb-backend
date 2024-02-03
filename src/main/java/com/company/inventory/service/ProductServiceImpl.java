package com.company.inventory.service;

import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.repository.ICategoryRepository;
import com.company.inventory.repository.IProductRepository;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    private final ICategoryRepository iCategoryRepository;
    private final IProductRepository iProductRepository;

    public ProductServiceImpl(ICategoryRepository iCategoryRepository, IProductRepository iProductRepository) {
        this.iCategoryRepository = iCategoryRepository;
        this.iProductRepository = iProductRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();

        try {
            // search for a catgory to be set at the product
            Optional<Category> category = iCategoryRepository.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product Created");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
            // save product
            Product productSaved = iProductRepository.save(product);
            productList.add(productSaved);
            productResponseRest.getProductResponseList().getProducts().add(productSaved);
            productResponseRest.setMetadata("SUCCESS", "00", "Product saved OK");
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not saved");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> findById(Long id) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();

        try {
            Optional<Product> product = iProductRepository.findById(id);
            if (product.isPresent()) {
                // descompress the image, this is necesary to render the image in the front
                // byte[] imageDescompressed = product.get().getImage();
                product.get().setImage(Image.decompressZLib(product.get().getImage()));

                productList.add(product.get());
                productResponseRest.getProductResponseList().setProducts(productList);
                productResponseRest.setMetadata("SUCCESS", "00", "Product found OK");
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not found");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> findByNameLike(String name) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();

        try {
            productList = iProductRepository.findByNameLike(name);
            if (!productList.isEmpty()) {
                for (Product product : productList) {
                    // descompress the image, this is necesary to render the image in the front
                    product.setImage(Image.decompressZLib(product.getImage()));
                }
                // the same loop with lambda
                // productList.forEach(product ->
                // product.setImage(Image.decompressZLib(product.getImage())));
                productResponseRest.getProductResponseList().setProducts(productList);
                productResponseRest.setMetadata("SUCCESS", "00", "Product found OK");
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not found");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> delete(Long id) {
        ProductResponseRest productResponseRest = new ProductResponseRest();

        try {
            Optional<Product> product = iProductRepository.findById(id);
            if (product.isPresent()) {
                iProductRepository.deleteById(id);
                productResponseRest.setMetadata("SUCCESS", "00", "Product deleted OK");
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not deleted");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();

        try {
            // search for a catgory to be set at the product
            Optional<Category> category = iCategoryRepository.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Category not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
            // update product
            Optional<Product> productToUpdate = iProductRepository.findById(product.getId());
            if (productToUpdate.isPresent()) {
                productToUpdate.get().setName(product.getName());
                productToUpdate.get().setPrice(product.getPrice());
                productToUpdate.get().setQuantity(product.getQuantity());
                productToUpdate.get().setImage(product.getImage());
                productToUpdate.get().setCategory(product.getCategory());

                Product productUpdated = iProductRepository.save(productToUpdate.get());
                productList.add(productUpdated);
                productResponseRest.getProductResponseList().getProducts().add(productUpdated);
                productResponseRest.setMetadata("SUCCESS", "00", "Product updated OK");
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not updated");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> findAll() {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();

        try {
            productList = iProductRepository.findAll();
            if (!productList.isEmpty()) {
                for (Product product : productList) {
                    // descompress the image, this is necesary to render the image in the front
                    product.setImage(Image.decompressZLib(product.getImage()));
                }
                productResponseRest.getProductResponseList().setProducts(productList);
                productResponseRest.setMetadata("SUCCESS", "00", "Product found OK");
            } else {
                productResponseRest.setMetadata("SUCCESS", "-1", "Product not found");
                return new ResponseEntity<>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("ERROR", "-1", "Product not found");
            return new ResponseEntity<>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponseRest, HttpStatus.OK);
    }

}
