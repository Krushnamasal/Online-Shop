package com.altimetrik.online_shop.product_service.products.service;

import com.altimetrik.admin_service.admin_service.admin.dto.AttributeDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.InventoryDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.ProductDTO;
import com.altimetrik.admin_service.admin_service.admin.dto.PriceDTO;
import com.altimetrik.online_shop.product_service.products.entity.Attribute;
import com.altimetrik.online_shop.product_service.products.entity.Inventory;
import com.altimetrik.online_shop.product_service.products.entity.Price;
import com.altimetrik.online_shop.product_service.products.entity.Product;
import com.altimetrik.online_shop.product_service.products.exception.ResourceNotFoundException;
import com.altimetrik.online_shop.product_service.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByCategory(String category, Optional<String> sortBy, Optional<String> order) {
        // Validate the category
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        // Determine the sort order, defaulting to ascending
        boolean isAscending = order.map(o -> o.equalsIgnoreCase("asc")).orElse(true);

        // Apply sorting based on the sortBy parameter
        if (sortBy.isPresent()) {
            switch (sortBy.get().toLowerCase()) {
                case "price":
                    return isAscending
                            ? productRepository.findByCategoryOrderByPriceAmountAsc(category)
                            : productRepository.findByCategoryOrderByPriceAmountDesc(category);
                case "inventory":
                    return isAscending
                            ? productRepository.findByCategoryOrderByInventoryAvailableAsc(category)
                            : productRepository.findByCategoryOrderByInventoryAvailableDesc(category);
                default:
                    // Default to unsorted if sortBy is not recognized
                    return productRepository.findByCategory(category);
            }
        } else {
            // Default to unsorted if sortBy is not provided
            return productRepository.findByCategory(category);
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        Price price = new Price();
        price.setCurrency(productDTO.getPrice().getCurrency());
        price.setAmount(productDTO.getPrice().getAmount());
        product.setPrice(price);
        price.setProduct(product);

        Inventory inventory = new Inventory();
        inventory.setTotal(productDTO.getInventory().getTotal());
        inventory.setAvailable(productDTO.getInventory().getAvailable());
        inventory.setReserved(productDTO.getInventory().getReserved());
        product.setInventory(inventory);
        inventory.setProduct(product);

        List<Attribute> attributes = productDTO.getAttributes().stream()
                .map(attr -> {
                    Attribute attribute = new Attribute();
                    attribute.setName(attr.getName());
                    attribute.setValue(attr.getValue());
                    attribute.setProduct(product); // Set bidirectional relationship
                    return attribute;
                })
                .collect(Collectors.toList());
        product.setAttributes(attributes);


        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setBrand(product.getBrand());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory());

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(product.getPrice().getId());
        priceDTO.setCurrency(product.getPrice().getCurrency());
        priceDTO.setAmount(product.getPrice().getAmount());
        productDTO.setPrice(priceDTO);

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(product.getInventory().getId());
        inventoryDTO.setTotal(product.getInventory().getTotal());
        inventoryDTO.setAvailable(product.getInventory().getAvailable());
        inventoryDTO.setReserved(product.getInventory().getReserved());
        productDTO.setInventory(inventoryDTO);

        productDTO.setAttributes(product.getAttributes().stream()
                .map(attr -> {
                    AttributeDTO attributeDTO = new AttributeDTO();
                    attributeDTO.setId(attr.getId());
                    attributeDTO.setName(attr.getName());
                    attributeDTO.setValue(attr.getValue());
                    return attributeDTO;
                })
                .collect(Collectors.toList()));

        return productDTO;
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (!existingProductOpt.isPresent()) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        Product existingProduct = existingProductOpt.get();
        // Update fields of existing product with the values from productDTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(productDTO.getCategory());

        Price price = existingProduct.getPrice();
        if (price == null) {
            price = new Price();
        }
        price.setCurrency(productDTO.getPrice().getCurrency());
        price.setAmount(productDTO.getPrice().getAmount());
        existingProduct.setPrice(price);
        price.setProduct(existingProduct);

        Inventory inventory = existingProduct.getInventory();
        if (inventory == null) {
            inventory = new Inventory();
        }
        inventory.setTotal(productDTO.getInventory().getTotal());
        inventory.setAvailable(productDTO.getInventory().getAvailable());
        inventory.setReserved(productDTO.getInventory().getReserved());
        existingProduct.setInventory(inventory);
        inventory.setProduct(existingProduct);

        existingProduct.setAttributes(productDTO.getAttributes().stream()
                .map(attr -> {
                    Attribute attribute = new Attribute();
                    attribute.setName(attr.getName());
                    attribute.setValue(attr.getValue());
                    attribute.setProduct(existingProduct);
                    return attribute;
                })
                .collect(Collectors.toList()));

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    public void removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
