package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

public class Topping {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    // Nếu backend có trả về description cho topping thì thêm vào
     @SerializedName("description")
     private String description;

    @SerializedName("price")
    private Double price; // Dùng Double vì giá có thể lẻ

    // Nếu backend có trả về available cho topping thì thêm vào
     @SerializedName("available")
     private Boolean available;


    public Topping() {
    }

    public Topping(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

     public String getDescription() { return description; }
     public Boolean getAvailable() { return available; }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
     public void setDescription(String description) { this.description = description; }
     public void setAvailable(Boolean available) { this.available = available; }
}
