package com.altimetrik.admin_service.admin_service.admin.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private int total;
    private int available;
    private int reserved;

}
