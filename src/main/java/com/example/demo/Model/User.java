package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.aspectj.weaver.ast.Or;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@AllArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    @EqualsAndHashCode.Exclude
    private Set<Order> orders;

    public User() {
        cart = new Cart();
        orders = new HashSet<>();
    }

    public User(String userId) {
        // Chuyển đổi chuỗi UUID thành UUID và gán cho thuộc tính id
        this.id = UUID.fromString(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                '}';
    }
}
