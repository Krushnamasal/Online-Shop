package com.altimetrik.admin_service.admin_service.admin.dto;

import lombok.Data;

@Data
public class AttributeDTO {
    private Long id;
    private String name;
    private String value;
    private  int productId;
}
