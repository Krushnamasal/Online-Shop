package com.altimetrik.online_shop.customer_service.customer_service.customer.service;

import com.altimetrik.online_shop.customer_service.customer_service.customer.externalService.ProductClient;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private ProductClient productClient;

    public List<Product> getProductsByCategory(String category, Optional<String> sortBy, Optional<String> order) {
        return productClient.getProductsByCategory(
                category,
                sortBy.orElse(null),
                order.orElse(null)
        );
    }
}
