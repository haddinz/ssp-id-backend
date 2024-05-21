package com.security.jwt.spring.models.repository;

import com.security.jwt.spring.models.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    Optional<Supplier> findByEmail(String email);
}
