package com.example.cnpmbackend.entity;
import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Set;
@Data
@Entity
@Table(name = "toppings")
@NoArgsConstructor
@AllArgsConstructor
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean available = true;

    @ManyToMany(mappedBy = "toppings", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<DrinkItem> drinkItems;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<DrinkItem> getDrinkItems() {
        return drinkItems;
    }

    public void setDrinkItems(Set<DrinkItem> drinkItems) {
        this.drinkItems = drinkItems;
    }
}
