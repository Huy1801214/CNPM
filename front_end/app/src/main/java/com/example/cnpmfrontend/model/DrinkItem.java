package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;
import java.util.Set;

public class DrinkItem {
    private int id;
    private String name;
    private String description;
    private Double price;

    @SerializedName("imageUrl")
    private String imageUrl;

    private DrinkCategory category;
    private Boolean available;
    private Set<Topping> toppings;

    public DrinkItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public DrinkCategory getCategory() { return category; }
    public void setCategory(DrinkCategory category) { this.category = category; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Set<Topping> getToppings() { return toppings; }
    public void setToppings(Set<Topping> toppings) { this.toppings = toppings; }
}