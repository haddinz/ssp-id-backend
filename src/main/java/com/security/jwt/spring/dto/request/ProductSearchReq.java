package com.security.jwt.spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchReq {
    private String name;
    private String minPrice;
    private String maxPrice;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
