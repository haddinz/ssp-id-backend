package com.security.jwt.spring.dto.response;

import com.security.jwt.spring.models.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private Category category;
}
