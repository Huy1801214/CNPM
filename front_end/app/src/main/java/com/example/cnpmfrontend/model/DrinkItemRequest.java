package com.example.cnpmfrontend.model;

import java.util.Set;

public class DrinkItemRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean available;
    private Integer categoryId;
    private Set<Integer> toppingIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Set<Integer> getToppingIds() {
        return toppingIds;
    }

    public void setToppingIds(Set<Integer> toppingIds) {
        this.toppingIds = toppingIds;
    }
}
