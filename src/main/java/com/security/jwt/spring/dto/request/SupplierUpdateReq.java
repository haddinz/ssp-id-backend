package com.security.jwt.spring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierUpdateReq {
    @JsonIgnore
    private String id;

    private String name;
    private String address;
    private String email;
}
