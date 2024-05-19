package com.security.jwt.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateReq {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
