package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Data
@Table(name = "cart")
public class Cart extends BaseModel {
    @Column(name = "totalAmount")
    private double totalAmount;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("cart")
    @EqualsAndHashCode.Exclude
    private Set<CartProduct> cartProducts;

    public Cart() {
        totalAmount = 0;
        cartProducts = new HashSet<>();
    }

    public double calcTotal() {
        double total = 0.0;
        for(CartProduct cartProduct : cartProducts) {
            total += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        }
        return total;
    }

    public CartProduct changeQuantityCartProduct(UUID productId, Integer quantity) {
        CartProduct cartProduct = findCardProductByProductId(productId);
        if(cartProduct != null) {
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
            if(cartProduct.getQuantity() < 1) {
                cartProducts.remove(cartProduct);
            }
            totalAmount = calcTotal();
            return cartProduct;
        }
        return null;
    }

    public CartProduct findCardProductByProductId(UUID productId) {
        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(productId)) {
                return cartProduct;
            }
        }
        return null;
    }

    public void removeCartProduct(CartProduct cartProduct) {
        System.out.println(        cartProducts.remove(cartProduct));
        totalAmount = calcTotal();
    }}
