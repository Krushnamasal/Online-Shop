package com.altimetrik.admin_service.admin_service.admin.service;

import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.admin_service.admin_service.admin.externalService.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private ProductClient productClient;

    public ProductDTO addProduct(ProductDTO product) {
        return productClient.addProduct(product);
    }

    public void removeProduct(Long productId) {
        productClient.removeProduct(productId);
    }

    public ProductDTO updateProduct(Long id,ProductDTO product) {
        return productClient.updateProduct(id,product);
    }
}
