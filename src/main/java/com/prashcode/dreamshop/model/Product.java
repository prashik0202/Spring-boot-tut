package com.prashcode.dreamshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // auto generated value


    private String name; // name of product
    private String brand; // brand name which that product belongs to e.g. apple
    private BigDecimal price; // price of product
    private int inventory; // quantity of products in available
    private String description; // about the product

    /*
        Category does not depend on product nor product depend on category
        so deletion of product does not delete category
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private  Category category;

    /*
        Image is depend on product
        so deletion of product leads to deletion of images associated with it
     */
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
