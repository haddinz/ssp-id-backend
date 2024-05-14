package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.response.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(
            path = "/demo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> get() {
        return WebResponse.<String>builder().data("success getting secured url").message("200 ok").build();
    }

    @GetMapping(
            path = "/demo/admin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> getAdmin() {
        return WebResponse.<String>builder().data("success getting admin url").message("200 ok").build();
    }

}
