package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Comment extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("categories")
    Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"orders", "cart","username", "phone", "email", "password"})
    User user;
    @Column(name = "comment")
    String cmt;
}
