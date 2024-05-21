package com.security.jwt.spring.dto.response;

import com.security.jwt.spring.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private String id;
    private String name;
    private String address;
    private String email;
    private List<Product> productsList;
}
