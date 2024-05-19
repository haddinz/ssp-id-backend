package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.ProductCreateReq;
import com.security.jwt.spring.dto.request.ProductSearchReq;
import com.security.jwt.spring.dto.request.ProductUpdateReq;
import com.security.jwt.spring.dto.response.PagingResponse;
import com.security.jwt.spring.dto.response.ProductResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "/api/product/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> create(@RequestBody ProductCreateReq dto) {
        ProductResponse productResponse = productService.create(dto);
        return WebResponse.<ProductResponse>builder().data(productResponse).message("product created successfully").build();
    }

    @GetMapping(
            path = "/api/product/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponseList = productService.findAll();
        return WebResponse.<List<ProductResponse>>builder().data(productResponseList).build();
    }

    @GetMapping(
            path = "/api/product/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> get(@PathVariable String productId) {
        ProductResponse productResponse = productService.get(productId);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @PutMapping(
            path = "/api/product/update/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> update(@RequestBody ProductUpdateReq dto, @PathVariable String productId) {
        dto.setId(productId);

        ProductResponse productResponse = productService.update(dto, productId);
        return WebResponse.<ProductResponse>builder().data(productResponse).message("update product successfully").build();
    }

    @DeleteMapping(
            path = "/api/product/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(@PathVariable String productId) {
        productService.remove(productId);
        return WebResponse.<String>builder().data("product deleted successfully").build();
    }

    @GetMapping(
            path = "/api/product/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductResponse>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) String minPrice,
            @RequestParam(value = "maxPrice", required = false) String maxPrice,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        ProductSearchReq dto = ProductSearchReq.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .build();

        Page<ProductResponse> productResponses = productService.search(dto);
        return WebResponse.<List<ProductResponse>>builder()
                .data(productResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(productResponses.getNumber())
                        .totalPage(productResponses.getTotalPages())
                        .size(productResponses.getSize())
                        .build())
                .build();
    }
}

