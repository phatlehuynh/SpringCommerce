package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cart")
public class Cart extends BaseModel {
    @Column(name = "totalAmount")
    private double totalAmount;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cart")
    @EqualsAndHashCode.Exclude
    private Set<CartProduct> cartProducts;

    public double calcTotal() {
        double total = 0.0;
        for(CartProduct cartProduct : cartProducts) {
            total += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        }
        return total;
    }

    public void addCartProduct(CartProduct cartProduct) {
        cartProducts.add(cartProduct);
        totalAmount = calcTotal();
    }
}
