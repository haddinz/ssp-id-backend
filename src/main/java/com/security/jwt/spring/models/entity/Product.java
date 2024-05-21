package com.security.jwt.spring.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
public class Product {
    @Id
    @Column(name = "product_id", unique = true)
    private String id;

    @Column(name = "product_name" ,nullable = false, length = 100)
    private String name;

    @Column(name = "product_quantity" ,nullable = false, length = 100)
    private Integer quantity;

    @Column(name = "product_desc" ,nullable = false, length = 500)
    private String description;

    @Column(name = "product_price" ,nullable = false, length = 100)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "tbl_product_suppliers",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private List<Supplier> supplierList;
}
