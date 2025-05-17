package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

public class Topping {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private Double price;

    @SerializedName("available")
    private Boolean available;

    public Topping() {
    }

    public Topping(int id, String name, Double price, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public String getDescription() { return description; }
    public Boolean getAvailable() { return available; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setAvailable(Boolean available) { this.available = available; }
}