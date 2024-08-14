package com.altimetrik.online_shop.customer_service.customer_service.customer.controller;

import com.altimetrik.online_shop.customer_service.customer_service.customer.exceptionHandler.InvalidInputException;
import com.altimetrik.online_shop.customer_service.customer_service.customer.exceptionHandler.ResourceNotFoundException;
import com.altimetrik.online_shop.customer_service.customer_service.customer.service.CustomerService;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/rest/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/products")
    public List<Product> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidInputException("Category cannot be null or empty");
        }

        List<Product> products = customerService.getProductsByCategory(category, Optional.ofNullable(sortBy), Optional.ofNullable(order));

        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for the category: " + category);
        }
        return products;
    }
}
