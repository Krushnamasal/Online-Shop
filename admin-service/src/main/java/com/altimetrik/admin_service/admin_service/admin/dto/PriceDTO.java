package com.altimetrik.admin_service.admin_service.admin.dto;

import lombok.Data;

@Data
public class PriceDTO {
    private Long id;
    private String currency;
    private double amount;
}
