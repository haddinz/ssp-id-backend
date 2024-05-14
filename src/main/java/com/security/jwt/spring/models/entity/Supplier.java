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
    @Column(name = "id_supplier",nullable = false, unique = true)
    private String id;

    @Column(name = "name_supplier", nullable = false)
    private String name;

    @Column(name = "address_supplier", nullable = false)
    private String address;

    @Column(name = "email_supplier", nullable = false)
    private String email;

    @OneToMany(mappedBy = "supplierSet")
    private List<Product> productsList;
}
