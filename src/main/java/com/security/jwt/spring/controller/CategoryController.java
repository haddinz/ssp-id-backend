package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.CategoryCreateReq;
import com.security.jwt.spring.dto.request.CategoryUpdateReq;
import com.security.jwt.spring.dto.response.CategoryResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(
            path = "/category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> create(@RequestBody CategoryCreateReq dto) {
        CategoryResponse categoryResponse = categoryService.create(dto);
        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }

    @PostMapping(
            path = "/category/{categoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> update(@RequestBody CategoryUpdateReq dto, @PathVariable("categoryId") String categoryId) {
        dto.setId(categoryId);

        CategoryResponse categoryResponse = categoryService.update(categoryId, dto);
        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }
}
