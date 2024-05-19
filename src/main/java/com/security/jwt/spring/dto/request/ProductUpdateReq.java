package com.security.jwt.spring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateReq {
    @JsonIgnore
    @NotBlank
    private String id;
    private String name;
    private Integer quantity;
    private String description;
    private double price;
}
