package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.CategoryCreateReq;
import com.security.jwt.spring.dto.request.CategoryUpdateReq;
import com.security.jwt.spring.dto.response.CategoryResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(
            path = "/api/admin/category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> create(@RequestBody CategoryCreateReq dto) {
        CategoryResponse categoryResponse = categoryService.create(dto);
        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }

    @PutMapping(
            path = "/api/admin/category/{categoryId}",
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

    @GetMapping(
            path = "/api/category/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categoryResponse = categoryService.findAll();
        return WebResponse.<List<CategoryResponse>>builder()
                .data(categoryResponse)
                .build();
    }

    @GetMapping(
            path = "/api/admin/category/{categoryId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> findOne(@PathVariable("categoryId") String categoryId) {
        CategoryResponse categoryResponse = categoryService.findOne(categoryId);
        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }

    @DeleteMapping(
            path = "/api/admin/category/{categoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(@PathVariable("categoryId") String categoryId) {
        categoryService.remove(categoryId);
        return WebResponse.<String>builder()
                .data("remove category successfully")
                .build();
    }
}
