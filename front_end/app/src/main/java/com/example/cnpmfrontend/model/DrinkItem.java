package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;
import java.util.Set;

public class DrinkItem {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private Double price;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("category")
    private SimpleDrinkCategory category;

    @SerializedName("available")
    private Boolean available;

    @SerializedName("toppings")
    private Set<SimpleTopping> toppings;

    public DrinkItem() {}

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public SimpleDrinkCategory getCategory() { return category; }
    public Boolean getAvailable() { return available; }
    public Set<SimpleTopping> getToppings() { return toppings; }

    // Setters (Tùy chọn)
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCategory(SimpleDrinkCategory category) { this.category = category; }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setToppings(Set<SimpleTopping> toppings) { this.toppings = toppings; }
}