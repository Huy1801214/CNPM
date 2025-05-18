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


}
