package com.altimetrik.online_shop.customer_service.customer_service.customer.controller;

import com.altimetrik.online_shop.customer_service.customer_service.customer.service.CustomerService;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import com.altimetrik.online_shop.product_service.products.entity.Price;
import com.altimetrik.online_shop.product_service.products.entity.Inventory;
import com.altimetrik.online_shop.product_service.products.entity.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        String category = "electronics";

        // Create sample product with detailed structure
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setBrand("Brand Name");
        product.setDescription("Powerful and stylish smartphone with high-end features.");
        product.setCategory(category);

        Price price = new Price();
        price.setId(1L);
        price.setCurrency("USD");
        price.setAmount(799.99);
        product.setPrice(price);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setTotal(50);
        inventory.setAvailable(40);
        inventory.setReserved(10);
        product.setInventory(inventory);

        Attribute colorAttribute = new Attribute();
        colorAttribute.setId(1L);
        colorAttribute.setName("Color");
        colorAttribute.setValue("Black");

        Attribute storageAttribute = new Attribute();
        storageAttribute.setId(2L);
        storageAttribute.setName("Storage");
        storageAttribute.setValue("128GB");

        product.setAttributes(Arrays.asList(colorAttribute, storageAttribute));

        List<Product> mockProducts = Arrays.asList(product);

        when(customerService.getProductsByCategory(category, Optional.empty(), Optional.empty()))
                .thenReturn(mockProducts);

        mockMvc.perform(get("/api/rest/v1/customer/products")
                        .param("category", category))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Smartphone\",\"brand\":\"Brand Name\",\"description\":\"Powerful and stylish smartphone with high-end features.\",\"category\":\"electronics\",\"price\":{\"id\":1,\"currency\":\"USD\",\"amount\":799.99},\"inventory\":{\"id\":1,\"total\":50,\"available\":40,\"reserved\":10},\"attributes\":[{\"id\":1,\"name\":\"Color\",\"value\":\"Black\"},{\"id\":2,\"name\":\"Storage\",\"value\":\"128GB\"}]}]"));
    }

    @Test
    void testGetProductsByCategoryWithSorting() throws Exception {
        String category = "electronics";
        String sortBy = "price";
        String order = "asc";

        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setBrand("Brand Name");
        product.setDescription("Powerful and stylish smartphone with high-end features.");
        product.setCategory(category);

        Price price = new Price();
        price.setId(1L);
        price.setCurrency("USD");
        price.setAmount(799.99);
        product.setPrice(price);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setTotal(50);
        inventory.setAvailable(40);
        inventory.setReserved(10);
        product.setInventory(inventory);

        Attribute colorAttribute = new Attribute();
        colorAttribute.setId(1L);
        colorAttribute.setName("Color");
        colorAttribute.setValue("Black");

        Attribute storageAttribute = new Attribute();
        storageAttribute.setId(2L);
        storageAttribute.setName("Storage");
        storageAttribute.setValue("128GB");

        product.setAttributes(Arrays.asList(colorAttribute, storageAttribute));

        List<Product> mockProducts = Arrays.asList(product);

        when(customerService.getProductsByCategory(category, Optional.of(sortBy), Optional.of(order)))
                .thenReturn(mockProducts);

        mockMvc.perform(get("/api/rest/v1/customer/products")
                        .param("category", category)
                        .param("sortBy", sortBy)
                        .param("order", order))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Smartphone\",\"brand\":\"Brand Name\",\"description\":\"Powerful and stylish smartphone with high-end features.\",\"category\":\"electronics\",\"price\":{\"id\":1,\"currency\":\"USD\",\"amount\":799.99},\"inventory\":{\"id\":1,\"total\":50,\"available\":40,\"reserved\":10},\"attributes\":[{\"id\":1,\"name\":\"Color\",\"value\":\"Black\"},{\"id\":2,\"name\":\"Storage\",\"value\":\"128GB\"}]}]"));
    }
}
