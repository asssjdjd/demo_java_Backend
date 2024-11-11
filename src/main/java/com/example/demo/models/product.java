package com.example.demo.models;

import jakarta.persistence.*;
import java.util.*;

import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String name;
    private String brand;
    private String category;


    @Column(columnDefinition = "TEXT")
    private String descirption;
    private Date cretedAt;
    private String imageFileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCretedAt() {
        return cretedAt;
    }

    public void setCretedAt(Date cretedAt) {
        this.cretedAt = cretedAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public String getDescirption() {
        return descirption;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setDescirption(String descirption) {
        this.descirption = descirption;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

