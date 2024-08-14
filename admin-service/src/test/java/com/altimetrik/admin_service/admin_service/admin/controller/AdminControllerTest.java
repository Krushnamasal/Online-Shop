package com.altimetrik.admin_service.admin_service.admin.controller;

import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.admin_service.admin_service.admin.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testAddProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Smartphone");
        productDTO.setBrand("Brand Name");

        when(adminService.addProduct(productDTO)).thenReturn(productDTO);

        mockMvc.perform(post("/api/rest/v1/admin/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Smartphone\",\"brand\":\"Brand Name\"}"));
    }

    @Test
    void testRemoveProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/rest/v1/admin/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed successfully"));

        verify(adminService).removeProduct(productId);
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("Smartphone");
        productDTO.setBrand("Updated Brand Name");

        when(adminService.updateProduct(productId, productDTO)).thenReturn(productDTO);

        mockMvc.perform(put("/api/rest/v1/admin/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Smartphone\",\"brand\":\"Updated Brand Name\"}"));
    }
}
