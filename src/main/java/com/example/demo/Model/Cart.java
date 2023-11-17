package com.example.demo.Model;

import com.example.demo.Utilities.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.Public.class)
    @Column(name = "totalAmount")
    private double totalAmount;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonIgnoreProperties("cart")
    @EqualsAndHashCode.Exclude
    @JsonView(Views.Public.class)
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

    public boolean containProduct(UUID productId) {
        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public void removeCartProduct(CartProduct cartProduct) {
        cartProducts.remove(cartProduct);
        totalAmount = calcTotal();
    }

    public void addCartProduct(CartProduct cartProduct) {
        cartProducts.add(cartProduct);
        totalAmount = calcTotal();
    }

    @Override
    public String toString() {
        return "Cart{" +
                ", id=" + id +
                "totalAmount=" + totalAmount +
                ", cartProducts=" + cartProducts +
                '}';
    }
}
