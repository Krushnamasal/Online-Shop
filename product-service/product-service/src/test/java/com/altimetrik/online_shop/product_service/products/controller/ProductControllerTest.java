package com.altimetrik.online_shop.product_service.products.controller;


import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import com.altimetrik.online_shop.product_service.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");

        List<Product> products = Arrays.asList(product);

        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/rest/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Smartphone\"}]"));
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");

        List<Product> products = Arrays.asList(product);

        when(productService.getProductsByCategory("electronics", Optional.empty(), Optional.empty()))
                .thenReturn(products);

        mockMvc.perform(get("/api/rest/v1/products/By")
                        .param("category", "electronics"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Smartphone\"}]"));
    }

    @Test
    void testAddProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Smartphone");

        when(productService.addProduct(productDTO)).thenReturn(productDTO);

        mockMvc.perform(post("/api/rest/v1/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Smartphone\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Smartphone\"}"));
    }

    @Test
    void testRemoveProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/rest/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed successfully"));

        verify(productService).removeProduct(productId);
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Updated Smartphone");

        when(productService.updateProduct(productId, productDTO)).thenReturn(productDTO);

        mockMvc.perform(put("/api/rest/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Smartphone\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Updated Smartphone\"}"));
    }
}
