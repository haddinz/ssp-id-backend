package com.security.jwt.spring.service;

import com.security.jwt.spring.dto.request.CategoryCreateReq;
import com.security.jwt.spring.dto.request.CategoryUpdateReq;
import com.security.jwt.spring.dto.response.CategoryResponse;
import com.security.jwt.spring.models.entity.Category;
import com.security.jwt.spring.models.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse create(CategoryCreateReq dto) {
        validationService.validation(dto);

        Category findCategory = categoryRepository.findByName(dto.getName());

        if(findCategory != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "category already exists");
        }

        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName(dto.getName());
        categoryRepository.save(category);

        return categoryResponse(category);
    }

    @Transactional
    public CategoryResponse update(String categoryId, CategoryUpdateReq dto) {
        validationService.validation(dto);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "category not found"));

        category.setName(dto.getName());
        categoryRepository.save(category);

        return categoryResponse(category);
    }

    private CategoryResponse categoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
