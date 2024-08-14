package com.altimetrik.online_shop.product_service.products.controller;

import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import com.altimetrik.online_shop.product_service.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/By")
    public List<Product> getProductsByCategory(@RequestParam String category,@RequestParam(required = false) String sortBy,@RequestParam(required = false) String order) {
        return productService.getProductsByCategory(category, Optional.ofNullable(sortBy),Optional.ofNullable(order));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.ok("Product removed successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }


}
