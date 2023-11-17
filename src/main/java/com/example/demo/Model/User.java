package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends BaseModel {
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "cart_Id")
    private UUID cartId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JsonIgnore
    @JoinColumn(name = "cart_id",referencedColumnName = "id", insertable = false, updatable = false)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("user")
    @EqualsAndHashCode.Exclude
    private Set<Order> orders;

    public void createCartAndOrder() {
        cart = new Cart();
        cartId = cart.getId();
        System.out.println(cart);
        orders = new HashSet<>();
    }

}
