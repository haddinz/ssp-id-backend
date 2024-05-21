package com.security.jwt.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierCreateReq {
    private String name;
    private String address;
    private String email;
}
