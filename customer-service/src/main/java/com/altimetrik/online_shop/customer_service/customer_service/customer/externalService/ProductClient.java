package com.altimetrik.online_shop.customer_service.customer_service.customer.externalService;

import com.altimetrik.online_shop.product_service.products.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/rest/v1/products/By")
    List<Product> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order);

}
