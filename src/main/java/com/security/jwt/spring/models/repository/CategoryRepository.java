package com.security.jwt.spring.models.repository;

import com.security.jwt.spring.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
     Category findByName(String name);
}
