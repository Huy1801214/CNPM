package com.example.cnpmbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class DrinkCategoryDTO {
    private Integer id;
    private String name;
    private String description;

    public DrinkCategoryDTO() {
    }

    public DrinkCategoryDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}