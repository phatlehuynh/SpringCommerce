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
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

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

}
