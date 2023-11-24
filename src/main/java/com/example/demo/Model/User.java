package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User extends BaseModel implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public void createCartAndOrder() {
        cart = new Cart();
        cartId = cart.getId();
        System.out.println(cart);
        orders = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }

}
