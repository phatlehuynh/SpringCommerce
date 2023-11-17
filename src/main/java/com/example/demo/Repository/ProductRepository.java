package com.example.demo.Repository;

import com.example.demo.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    public Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);
    @Query("SELECT p from Product p WHERE p.name LIKE CONCAT('%', :keyword, '%')")
    public Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p from Product p WHERE p.deleted = false ")
    Page<Product> findAll(Pageable pageable);
}
