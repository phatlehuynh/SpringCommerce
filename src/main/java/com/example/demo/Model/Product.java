package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends BaseModel{
    @Column(name = "title")
    private String title;

    @Column(name = "sumary")
    private String sumary;

    @Column(name = "sku")
    private int sku; // shop keeping unit

    @Column(name = "discount")
    private double discount;

    @Column(name = "quantity")
    private short quantity;     // old new

    @Column(name = "shop")
    private byte shop;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "startsAt")
    private LocalDateTime startsAt; // Ngay mo quang cao

    @Column(name = "endsAt")
    private LocalDateTime endsAt; //

    @Column(name = "content")
    private String content;

    @ElementCollection
    private List<String> links = new ArrayList<String>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("products")
    private List<Category> categories = new ArrayList<>();
}
