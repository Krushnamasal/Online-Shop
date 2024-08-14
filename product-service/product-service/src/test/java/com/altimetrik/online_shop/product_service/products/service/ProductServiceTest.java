package com.altimetrik.online_shop.product_service.products.service;


import com.altimetrik.admin_service.admin_service.admin.dto.AttributeDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.InventoryDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.PriceDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.online_shop.product_service.products.entity.Attribute;
import com.altimetrik.online_shop.product_service.products.entity.Inventory;
import com.altimetrik.online_shop.product_service.products.entity.Price;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import com.altimetrik.online_shop.product_service.products.exception.ResourceNotFoundException;
import com.altimetrik.online_shop.product_service.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setup() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Smartphone");
        sampleProduct.setBrand("Brand Name");
        sampleProduct.setDescription("A high-end smartphone");
        sampleProduct.setCategory("electronics");

        Price price = new Price();
        price.setCurrency("USD");
        price.setAmount(799.99);
        sampleProduct.setPrice(price);

        Inventory inventory = new Inventory();
        inventory.setTotal(50);
        inventory.setAvailable(40);
        inventory.setReserved(10);
        sampleProduct.setInventory(inventory);

        Attribute attribute = new Attribute();
        attribute.setName("Color");
        attribute.setValue("Black");
        sampleProduct.setAttributes(Arrays.asList(attribute));
    }

    @Test
    void testGetProductsByCategory() {
        List<Product> products = Arrays.asList(sampleProduct);
        when(productRepository.findByCategory("electronics")).thenReturn(products);
        // Commented out unnecessary stubbings
        // when(productRepository.findByCategoryOrderByPriceAmountAsc("electronics")).thenReturn(products);
        // when(productRepository.findByCategoryOrderByInventoryAvailableAsc("electronics")).thenReturn(products);

        // Call service method
        List<Product> result = productService.getProductsByCategory("electronics", Optional.empty(), Optional.empty());

        // Verify and assert
        assertEquals(products, result);

        // Verify that the repository method was called with the expected argument
        verify(productRepository).findByCategory("electronics");
        // Verify other repository interactions if needed
    }

    @Test
    void testAddProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Smartphone");
        productDTO.setBrand("Brand Name");
        productDTO.setDescription("A high-end smartphone");
        productDTO.setCategory("electronics");

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setCurrency("USD");
        priceDTO.setAmount(799.99);
        productDTO.setPrice(priceDTO);

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setTotal(50);
        inventoryDTO.setAvailable(40);
        inventoryDTO.setReserved(10);
        productDTO.setInventory(inventoryDTO);

        AttributeDTO attributeDTO = new AttributeDTO();
        attributeDTO.setName("Color");
        attributeDTO.setValue("Black");
        productDTO.setAttributes(Arrays.asList(attributeDTO));

        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        // Call service method
        ProductDTO result = productService.addProduct(productDTO);

        // Verify and assert
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getBrand(), result.getBrand());
    }

    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Smartphone");
        productDTO.setBrand("Updated Brand");
        productDTO.setDescription("Updated description");
        productDTO.setCategory("electronics");

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setCurrency("USD");
        priceDTO.setAmount(699.99);
        productDTO.setPrice(priceDTO);

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setTotal(60);
        inventoryDTO.setAvailable(50);
        inventoryDTO.setReserved(10);
        productDTO.setInventory(inventoryDTO);

        AttributeDTO attributeDTO = new AttributeDTO();
        attributeDTO.setName("Color");
        attributeDTO.setValue("Blue");
        productDTO.setAttributes(Arrays.asList(attributeDTO));

        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        // Call service method
        ProductDTO result = productService.updateProduct(1L, productDTO);

        // Verify and assert
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getBrand(), result.getBrand());
    }

    @Test
    void testRemoveProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        doNothing().when(productRepository).delete(any(Product.class));

        // Call service method
        productService.removeProduct(1L);

        // Verify
        verify(productRepository).delete(sampleProduct);
    }

    @Test
    void testRemoveProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Call and assert exception
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.removeProduct(1L),
                "Expected removeProduct() to throw ResourceNotFoundException"
        );

        assertTrue(thrown.getMessage().contains("Product not found with id: 1"));
    }
}
