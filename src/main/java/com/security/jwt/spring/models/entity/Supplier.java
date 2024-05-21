package com.security.jwt.spring.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_supplier")
public class Supplier {
    @Id
    @Column(name = "supplier_id",nullable = false, unique = true)
    private String id;

    @Column(name = "supplier_name", nullable = false)
    private String name;

    @Column(name = "supplier_address", nullable = false)
    private String address;

    @Column(name = "supplier_email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "supplierList")
    private List<Product> productsList;
}
