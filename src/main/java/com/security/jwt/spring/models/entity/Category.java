package com.security.jwt.spring.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @Column(name = "category_id",nullable = false, unique = true)
    private String id;

    @Column(name = "category_name" ,nullable = false, length = 100)
    private String name;
}
