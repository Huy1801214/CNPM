package com.example.cnpmbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkItemDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private SimpleCategoryDTO category;
    private Boolean available;
    private Set<SimpleToppingDTO> toppings;

    public DrinkItemDTO() {
    }

    public DrinkItemDTO(Integer id, String name, String description, Double price, String imageUrl, SimpleCategoryDTO category, Boolean available, Set<SimpleToppingDTO> toppings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.available = available;
        this.toppings = toppings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public SimpleCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(SimpleCategoryDTO category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<SimpleToppingDTO> getToppings() {
        return toppings;
    }

    public void setToppings(Set<SimpleToppingDTO> toppings) {
        this.toppings = toppings;
    }
}
