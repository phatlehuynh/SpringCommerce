package com.example.demo.Model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order extends BaseModel {

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private boolean status;

    // Add any other order-specific properties here

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("orders")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
//    @JsonManagedReference
    @JsonIgnoreProperties("orders")
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();

    public double calculateTotalAmount() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
        this.totalAmount = calculateTotalAmount();
    }

    public void addProduct(Product product) {
        if(product != null) {
            this.products.add(product);
            this.totalAmount = calculateTotalAmount();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
