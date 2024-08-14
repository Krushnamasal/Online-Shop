package com.altimetrik.admin_service.admin_service.admin.service;


import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.admin_service.admin_service.admin.externalService.ProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setup() {
        // Any setup if needed
    }

    @Test
    void testAddProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Smartphone");
        productDTO.setBrand("Brand Name");

        // Set up mock behavior
        when(productClient.addProduct(productDTO)).thenReturn(productDTO);

        // Call the service method
        ProductDTO createdProduct = adminService.addProduct(productDTO);

        // Assert results
        assertEquals(productDTO, createdProduct);
    }

    @Test
    void testRemoveProduct() {
        Long productId = 1L;

        // Call the service method
        adminService.removeProduct(productId);

        // Verify that the removeProduct method was called on the mock
        verify(productClient).removeProduct(productId);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Smartphone");
        productDTO.setBrand("Updated Brand Name");

        // Set up mock behavior
        when(productClient.updateProduct(productId, productDTO)).thenReturn(productDTO);

        // Call the service method
        ProductDTO updatedProduct = adminService.updateProduct(productId, productDTO);

        // Assert results
        assertEquals(productDTO, updatedProduct);
    }
}
