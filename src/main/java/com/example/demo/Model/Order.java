package com.example.demo.Model;

import com.example.demo.Utilities.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonView(Views.Public.class)
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @JsonView(Views.Public.class)
    @Column(name = "address")
    private String address;

    @JsonView(Views.Public.class)
    @Column(name = "status")
    private byte status;

    @Column(name = "user_id")
    private UUID userId;

    @JsonView(Views.Public.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"cart", "orders","phone", "email", "password", "cartId", "role"})
    private User user;

    @Column(name = "cart_id")
    private UUID cartId;

    @JsonView(Views.Public.class)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;
}
