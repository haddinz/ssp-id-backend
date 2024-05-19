package com.security.jwt.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.spring.dto.request.LoginRequest;
import com.security.jwt.spring.dto.request.RegisterRequest;
import com.security.jwt.spring.dto.response.AuthenticationResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.models.entity.Role;
import com.security.jwt.spring.models.entity.User;
import com.security.jwt.spring.models.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    void testRegisterSuccess() throws Exception {
        RegisterRequest dto = new RegisterRequest();
        dto.setFirstName("Mail Maulana");
        dto.setLastName("Suryana");
        dto.setUsername("@mail");
        dto.setEmail("mail@gmail.com");
        dto.setPassword("password");

        mockMvc.perform(
                post("/api/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AuthenticationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertEquals("register successfully", response.getData().getMessage());
        });
    }

    void testLoginSuccess() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName("Mail Maulana");
        user.setLastName("Suryana");
        user.setUsername("@mail");
        user.setEmail("email@gmail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        userRepository.save(user);

        LoginRequest dto = new LoginRequest();
        dto.setEmail("email@gmail.com");
        dto.setPassword("password");
        dto.setUsername("@mail");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AuthenticationResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertEquals("authenticate successfully", response.getData().getMessage());
        });
    }
}
