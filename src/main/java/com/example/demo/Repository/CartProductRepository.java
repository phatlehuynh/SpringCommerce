package com.example.demo.Repository;

import com.example.demo.Model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {
}
