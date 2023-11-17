package com.example.demo.Model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import com.example.demo.Utilities.Views;

import java.time.LocalDateTime;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends BaseModel{
    @JsonView(Views.Public.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.Public.class)
    @Column(name = "price")
    private double price;

    @JsonView(Views.Public.class)
    @Column(name = "brand")
    private String brand;

    @JsonView(Views.Public.class)
    @Column(name = "color")
    private String color;

    @JsonView(Views.Public.class)
    @ElementCollection
    private List<String> linkImages = new ArrayList<String>();

    @Column(name = "category_id")
    private UUID categoryId;

    @JsonView(Views.HaveCategoty.class)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",  referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"products", "parentId"})
    private Category category;

    @JsonView(Views.HaveCategoty.class)
    @Column(name = "deleted")
    private boolean deleted = false;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
