package com.altimetrik.online_shop.customer_service.customer_service.customer.service;


import com.altimetrik.online_shop.customer_service.customer_service.customer.externalService.ProductClient;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CustomerService customerService;


    @Test
    void testGetProductsByCategory() {
        String category = "electronics";
        Optional<String> sortBy = Optional.empty();
        Optional<String> order = Optional.empty();

        // Create sample product
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setBrand("Brand Name");
        product.setDescription("Powerful and stylish smartphone with high-end features.");
        product.setCategory(category);

        // Set up mock behavior
        when(productClient.getProductsByCategory(category, null, null))
                .thenReturn(Arrays.asList(product));

        // Call the service method
        List<Product> products = customerService.getProductsByCategory(category, sortBy, order);

        // Assert results
        assertEquals(1, products.size());
        assertEquals("Smartphone", products.get(0).getName());
    }

    @Test
    void testGetProductsByCategoryWithSorting() {
        String category = "electronics";
        String sortBy = "price";
        String order = "asc";

        // Create sample product
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setBrand("Brand Name");
        product.setDescription("Powerful and stylish smartphone with high-end features.");
        product.setCategory(category);

        // Set up mock behavior
        when(productClient.getProductsByCategory(category, sortBy, order))
                .thenReturn(Arrays.asList(product));

        // Call the service method
        List<Product> products = customerService.getProductsByCategory(category, Optional.of(sortBy), Optional.of(order));

        // Assert results
        assertEquals(1, products.size());
        assertEquals("Smartphone", products.get(0).getName());
    }
}
