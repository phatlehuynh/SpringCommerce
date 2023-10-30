package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cart_product")
public class CartProduct extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("cartProducts")
    @EqualsAndHashCode.Exclude
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"linkImages", "categories"})
    @EqualsAndHashCode.Exclude
    private Product product;
    @EqualsAndHashCode.Exclude
    private int quantity;

    public void removeCartProduct() {
    }
}
