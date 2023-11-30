package com.example.demo.Model;

import com.example.demo.Utilities.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    @JsonIgnoreProperties({ "category", "categoryId", "user", "userId"})
    @EqualsAndHashCode.Exclude
    @JsonView(Views.Public.class)
    private Product product;

    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Exclude
    private int quantity;



    @Override
    public String toString() {
        return "CartProduct{" +
                ", id=" + id +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
