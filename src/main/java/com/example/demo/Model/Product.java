package com.example.demo.Model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import jakarta.persistence.*;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private int sku;

    @Column(name = "discount")
    private double discount;

    @Column(name = "quantity")
    private short quantity;

    @Column(name = "shop")
    private byte shop;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "startsAt")
    private LocalDateTime startsAt;

    @Column(name = "endsAt")
    private LocalDateTime endsAt;

    @Column(name = "content")
    private String content;
}
