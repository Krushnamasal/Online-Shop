package com.altimetrik.online_shop.product_service.products.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer total;
    private Integer available;
    private Integer reserved;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}
