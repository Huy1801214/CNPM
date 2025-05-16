package com.example.cnpmbackend.controllers;


import com.example.cnpmbackend.entity.DrinkCategory;
import com.example.cnpmbackend.entity.DrinkItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cnpmbackend.services.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/categories")
    public ResponseEntity<List<DrinkCategory>> getAllCategories() {
        List<DrinkCategory> categories = menuService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/drinks")
    public ResponseEntity<List<DrinkItem>> getAllDrinks() {
        List<DrinkItem> drinks = menuService.getAllDrinks();
        return ResponseEntity.ok(drinks);
    }

    @GetMapping("/drinks/category/{categoryId}")
    public ResponseEntity<List<DrinkItem>> getDrinksByCategory(@PathVariable Integer categoryId) {
        List<DrinkItem> drinks = menuService.getDrinksByCategoryId(categoryId);
        if (drinks.isEmpty()) {
            return ResponseEntity.noContent().build(); // Hoặc notFound() nếu category không tồn tại
        }
        return ResponseEntity.ok(drinks);
    }

    @GetMapping("/drinks/{drinkId}")
    public ResponseEntity<DrinkItem> getDrinkById(@PathVariable Integer drinkId) {
        DrinkItem drink = menuService.getDrinkById(drinkId);
        if (drink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(drink);
    }
}
