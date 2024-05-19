package com.security.jwt.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.spring.dto.request.ProductCreateReq;
import com.security.jwt.spring.dto.response.AuthenticationResponse;
import com.security.jwt.spring.dto.response.ProductResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.models.entity.Product;
import com.security.jwt.spring.models.entity.Role;
import com.security.jwt.spring.models.entity.Token;
import com.security.jwt.spring.models.entity.User;
import com.security.jwt.spring.models.repository.ProductRepository;
import com.security.jwt.spring.models.repository.TokenRepository;
import com.security.jwt.spring.models.repository.UserRepository;
import com.security.jwt.spring.service.ProductService;
import com.security.jwt.spring.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProduct() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("@test");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setRole(Role.USER);
        user.setEmail("usertest@egmail.com");
        user.setPassword("test");
        user = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);

        Random random = new Random();

        double randomPrice = random.nextDouble();
        int randomQuantity = random.nextInt();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setName("The Catcher in the Rye" + i);
            product.setDescription("A novel by J.D. Salinger about the events and circumstances that shape the life of a teenager, Holden Caulfield.");
            product.setPrice(12.08 + randomPrice);
            product.setQuantity(1 + randomQuantity);
            productRepository.save(product);
        }

        mockMvc.perform(
                get("/api/product/findAll")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ProductResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNotNull(response.getData());
        });
    }
}