package com.altimetrik.admin_service.admin_service.admin.controller;

import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.admin_service.admin_service.admin.exceptionHandler.InvalidInputException;
import com.altimetrik.admin_service.admin_service.admin.exceptionHandler.ResourceNotFoundException;
import com.altimetrik.admin_service.admin_service.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/rest/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = adminService.addProduct(productDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            ProductDTO emptyProductDTO = new ProductDTO();
            return new ResponseEntity<>(emptyProductDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable Long id) {
        try {
            adminService.removeProduct(id);
            return ResponseEntity.ok("Product removed successfully");
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = adminService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(productDTO, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            ProductDTO emptyProductDTO = new ProductDTO();
            return new ResponseEntity<>(emptyProductDTO, HttpStatus.NOT_FOUND);
        }
    }
}
