package com.security.jwt.spring.service;

import com.security.jwt.spring.dto.request.ProductCreateReq;
import com.security.jwt.spring.dto.request.ProductSearchReq;
import com.security.jwt.spring.dto.request.ProductUpdateReq;
import com.security.jwt.spring.dto.response.ProductResponse;
import com.security.jwt.spring.models.entity.Category;
import com.security.jwt.spring.models.entity.Product;
import com.security.jwt.spring.models.repository.CategoryRepository;
import com.security.jwt.spring.models.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse create(ProductCreateReq dto, String categoryId) {
        validationService.validation(dto);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        productRepository.save(product);

        return productResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(this::productResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse get(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        return productResponse(product);
    }

    @Transactional
    public ProductResponse update(ProductUpdateReq dto, String productId) {
        validationService.validation(dto);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        return productResponse(product);
    }

    @Transactional
    public void remove(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(ProductSearchReq dto) {
        Specification<Product> specification = ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(dto.getName())) {
                predicates.add(builder.like(root.get("name"), "%"+ dto.getName() +"%"));
            }

            if(Objects.nonNull(dto.getCategory())) {
                predicates.add(builder.equal(root.get("category").get("name"), dto.getCategory()));
            }

            if(Objects.nonNull(dto.getMinPrice())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), dto.getMinPrice() ));
            }

            if(Objects.nonNull(dto.getMaxPrice())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), dto.getMaxPrice()));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });

        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("price").ascending());
        Page<Product> products = productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream()
                .map(this::productResponse)
                .toList();

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    private ProductResponse productResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }
}
