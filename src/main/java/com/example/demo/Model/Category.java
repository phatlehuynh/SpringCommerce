package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category extends BaseModel {
    @Column(name = "title")
    private String title;
    @Column(name = "parentId")
    private UUID parentId;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("categories")
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();

}
