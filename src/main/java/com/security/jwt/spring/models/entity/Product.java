package com.security.jwt.spring.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
public class Product {
    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "name_product" ,nullable = false, length = 100)
    private String name;

    @Column(name = "quantity_product" ,nullable = false, length = 100)
    private Integer quantity;

    @Column(name = "description_product" ,nullable = false, length = 500)
    private String description;

    @Column(name = "price_product" ,nullable = false, length = 100)
    private double price;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "tbl_product_suppliers",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> supplierSet;
}
