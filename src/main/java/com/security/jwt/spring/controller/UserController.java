package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.UserUpdateReq;
import com.security.jwt.spring.dto.response.UserResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.models.entity.User;
import com.security.jwt.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(
            path = "/api/admin/user/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>> findAll() {
        List<UserResponse> userResponse = userService.findAll();
        return WebResponse.<List<UserResponse>>builder().data(userResponse).message("getting user successfully").build();
    }

    @GetMapping(
            path = "/api/admin/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(@PathVariable String userId) {
        UserResponse userResponse = userService.get(userId);
        return WebResponse.<UserResponse>builder().data(userResponse).message("getting user successfully").build();
    }

    @PutMapping(
            path = "/api/user/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(@RequestBody UserUpdateReq dto) {
        UserResponse userResponse = userService.update(dto);
        return WebResponse.<UserResponse>builder().data(userResponse).message("update user successfully").build();
    }
}
