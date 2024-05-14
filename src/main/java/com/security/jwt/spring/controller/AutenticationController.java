package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.LoginRequest;
import com.security.jwt.spring.dto.request.RegisterRequest;
import com.security.jwt.spring.dto.response.AuthenticationResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        return WebResponse.<AuthenticationResponse>builder().data(authenticationResponse).build();
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return WebResponse.<AuthenticationResponse>builder().data(authenticationResponse).build();
    }

    @PostMapping(
            path = "/refresh_token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request, response);
        return WebResponse.<AuthenticationResponse>builder().data(authenticationResponse).build();
    }
}
