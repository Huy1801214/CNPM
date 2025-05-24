package com.example.cnpmbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "drink_category")

public class DrinkCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DrinkItem> drinkItems;

    public DrinkCategory() {

    }

    public DrinkCategory(Integer id, String name, String description, List<DrinkItem> drinkItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.drinkItems = drinkItems;
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

    public List<DrinkItem> getDrinkItems() {
        return drinkItems;
    }

    public void setDrinkItems(List<DrinkItem> drinkItems) {
        this.drinkItems = drinkItems;
    }
}
