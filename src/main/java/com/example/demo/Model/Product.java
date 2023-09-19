package com.example.demo.Model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
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

    @Column(name = "content")
    private String content;

    @Column(name = "price")
    private double price;

    @Column(name = "sku")
    private int sku; // shop keeping unit

    @Column(name = "quantity")
    private short quantity;     // old new

    @Column(name = "shop")
    private byte shop;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "discount")
    private double discount;

    @Column(name = "startsAt")
    private LocalDateTime startsAt; // Ngay mo quang cao

    @Column(name = "endsAt")
    private LocalDateTime endsAt; //

    @Column(name = "link")
    private String link;


    @ElementCollection
    private List<String> linkImages = new ArrayList<String>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )


    @JsonIgnoreProperties("products")
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();


    public void addCategory(Category category) {
        if(category != null) {
            this.categories.add(category);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", sumary='" + sumary + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", sku=" + sku +
                ", quantity=" + quantity +
                ", shop=" + shop +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", discount=" + discount +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", link='" + link + '\'' +
                ", linkImages=" + linkImages +
                ", categories=" + categories +
                '}';
    }
}
