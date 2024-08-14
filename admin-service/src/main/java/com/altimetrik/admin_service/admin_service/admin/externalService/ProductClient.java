package com.altimetrik.admin_service.admin_service.admin.externalService;

import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface  ProductClient {
    @PostMapping("/api/rest/v1/products/add")
    ProductDTO addProduct(ProductDTO product);

    @DeleteMapping("/api/rest/v1/products/{id}")
    void removeProduct(@PathVariable Long id);

    @PutMapping("/api/rest/v1/products/{id}")
    ProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO product);
}
