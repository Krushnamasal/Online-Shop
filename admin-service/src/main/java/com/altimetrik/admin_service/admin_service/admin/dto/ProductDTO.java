package com.altimetrik.admin_service.admin_service.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String category;
    private PriceDTO price;
    private InventoryDTO inventory;
    private List<AttributeDTO> attributes;
}
