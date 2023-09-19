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

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private byte status;

    // Add any other order-specific properties here

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties({"cart", "orders"})
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
