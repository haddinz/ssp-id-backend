package com.security.jwt.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateReq {
    private String name;
    private Integer quantity;
    private String description;
    private double price;
}
