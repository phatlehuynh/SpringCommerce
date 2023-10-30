package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties({"cart", "orders"})
    private User user;

    @Column(name = "cart_id")
    private UUID cartId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;
}
