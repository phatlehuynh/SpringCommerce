package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<Product> products = new ArrayList<>();
}
