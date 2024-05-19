package com.security.jwt.spring.service;

import com.security.jwt.spring.dto.request.CategoryCreateReq;
import com.security.jwt.spring.dto.request.CategoryUpdateReq;
import com.security.jwt.spring.dto.response.CategoryResponse;
import com.security.jwt.spring.models.entity.Category;
import com.security.jwt.spring.models.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));

        category.setId(dto.getId());
        category.setName(dto.getName());
        categoryRepository.save(category);

        return categoryResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(this::categoryResponse).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findOne(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        return categoryResponse(category);
    }

    @Transactional
    public void remove(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));

        categoryRepository.delete(category);
    }

    private CategoryResponse categoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
