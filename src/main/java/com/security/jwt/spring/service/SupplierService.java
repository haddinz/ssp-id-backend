package com.security.jwt.spring.service;

import com.security.jwt.spring.dto.request.SupplierCreateReq;
import com.security.jwt.spring.dto.request.SupplierUpdateReq;
import com.security.jwt.spring.dto.response.SupplierResponse;
import com.security.jwt.spring.models.entity.Supplier;
import com.security.jwt.spring.models.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public SupplierResponse create(SupplierCreateReq dto) {
        validationService.validation(dto);

        Supplier supplier = supplierRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "email supplier already exist"));

        supplier.setId(UUID.randomUUID().toString());
        supplier.setName(dto.getName());
        supplier.setAddress(dto.getAddress());
        supplier.setEmail(dto.getEmail());
        supplierRepository.save(supplier);

        return supplierResponse(supplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> findAll() {
        List<Supplier> supplierList = supplierRepository.findAll();

        return supplierList.stream().map(this::supplierResponse).toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse get(String supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "supplier not found"));

        return supplierResponse(supplier);
    }

    @Transactional
    public SupplierResponse update(SupplierUpdateReq dto, String supplierId) {
        validationService.validation(dto);

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "supplier not found"));

        supplier.setName(dto.getName());
        supplier.setAddress(dto.getAddress());
        supplier.setEmail(dto.getEmail());
        supplierRepository.save(supplier);

        return supplierResponse(supplier);
    }

    @Transactional
    public void remove(String supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "supplier not found"));

        supplierRepository.delete(supplier);
    }

    private SupplierResponse supplierResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .email(supplier.getEmail())
                .build();
    }
}
