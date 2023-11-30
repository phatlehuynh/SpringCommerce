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
    public Page<Product> findByUserId(UUID userId, Pageable pageable);
    @Query("SELECT p from Product p WHERE p.name LIKE CONCAT('%', LOWER(:keyword), '%')")
    public Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT p from Product p WHERE p.deleted = false ")
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE"
            + "(:categoryId IS NULL OR p.category.id = :categoryId) "
            + "AND (:keyword IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:keyword), '%')) "
            + "AND (:brand IS NULL OR LOWER(p.brand) LIKE CONCAT('%', LOWER(:brand), '%')) "
            + "AND (:color IS NULL OR LOWER(p.color) LIKE CONCAT('%', LOWER(:color), '%'))"
            + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    public Page<Product> filter(
            @Param("categoryId") UUID categoryId,
            @Param("keyword") String keyword,
            @Param("brand") String brand,
            @Param("color") String color,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );


}
