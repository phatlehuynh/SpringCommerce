package com.example.demo.Model;

import com.example.demo.Utilities.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Random;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cart_product")
public class CartProduct extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "cart_id")
//    @JsonIgnoreProperties("cartProducts")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"category", "categoryId"})
    @EqualsAndHashCode.Exclude
    @JsonView(Views.Public.class)
    private Product product;

    @JoinColumn(name = "quantity")
    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Exclude
    private int quantity;

    @JsonView(Views.Public.class)
    @JoinColumn(name = "selected")
    @EqualsAndHashCode.Exclude
    private boolean selected;


    @Override
    public String toString() {
        return "CartProduct{" +
                ", id=" + id +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public CartProduct(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.selected = false;
    }
}
