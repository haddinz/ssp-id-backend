package com.security.jwt.spring.controller;

import com.security.jwt.spring.dto.request.SupplierCreateReq;
import com.security.jwt.spring.dto.request.SupplierUpdateReq;
import com.security.jwt.spring.dto.response.SupplierResponse;
import com.security.jwt.spring.dto.response.WebResponse;
import com.security.jwt.spring.models.entity.Supplier;
import com.security.jwt.spring.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping(
            path = "/api/supplier/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> create(@RequestBody SupplierCreateReq dto) {
        SupplierResponse supplierResponse = supplierService.create(dto);
        return WebResponse.<SupplierResponse>builder().data(supplierResponse).message("supplier create successfully").build();
    }

    @GetMapping(
            path = "/api/supplier/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<SupplierResponse>> findAll() {
        List<SupplierResponse> supplierResponseList = supplierService.findAll();
        return WebResponse.<List<SupplierResponse>>builder().data(supplierResponseList).message("supplier get list successfully").build();
    }

    @GetMapping(
            path = "/api/supplier/{supplierId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> get(@PathVariable String supplierId) {
        SupplierResponse supplierResponse = supplierService.get(supplierId);
        return WebResponse.<SupplierResponse>builder().data(supplierResponse).message("supplier get successfully").build();
    }

    @PutMapping(
            path = "/api/supplier/update/{supplierId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> update(@RequestBody SupplierUpdateReq dto, @PathVariable String supplierId) {
        dto.setId(supplierId);

        SupplierResponse supplierResponse = supplierService.update(dto, supplierId);
        return WebResponse.<SupplierResponse>builder().data(supplierResponse).message("supplier create successfully").build();
    }

    @DeleteMapping(
            path = "/api/supplier/remove/{supplierId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(@PathVariable String supplierId) {
        supplierService.remove(supplierId);

        return WebResponse.<String>builder().data("supplier remove successfully").build();
    }
}
