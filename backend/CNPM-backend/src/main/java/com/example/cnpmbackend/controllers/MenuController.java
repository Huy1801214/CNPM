package com.example.cnpmbackend.controllers;

import com.example.cnpmbackend.dto.DrinkCategoryDTO; // Import DTO
import com.example.cnpmbackend.dto.DrinkItemDTO;   // Import DTO
import com.example.cnpmbackend.dto.DrinkItemRequestDTO;
import com.example.cnpmbackend.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cnpmbackend.dto.DrinkCategoryDTO;
import com.example.cnpmbackend.dto.DrinkItemDTO;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/categories")
    public ResponseEntity<List<DrinkCategoryDTO>> getAllCategories() {
        List<DrinkCategoryDTO> categories = menuService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/drinks")
    public ResponseEntity<List<DrinkItemDTO>> getAllDrinks() {
        List<DrinkItemDTO> drinks = menuService.getAllDrinks();
        return ResponseEntity.ok(drinks);
    }

    @GetMapping("/drinks/category/{categoryId}")
    public ResponseEntity<List<DrinkItemDTO>> getDrinksByCategory(@PathVariable Integer categoryId) {
        List<DrinkItemDTO> drinks = menuService.getDrinksByCategoryId(categoryId);
        if (drinks == null || drinks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drinks);
    }

    @GetMapping("/drinks/{drinkId}")
    public ResponseEntity<DrinkItemDTO> getDrinkById(@PathVariable Integer drinkId) {
        DrinkItemDTO drink = menuService.getDrinkById(drinkId);
        if (drink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(drink);
    }

    // thêm món
    @PostMapping
    public ResponseEntity<String> addDrink(@RequestBody DrinkItemRequestDTO dto) {
        String result = menuService.addDrink(dto);
        return result.equals("Thêm thành công")
                ? ResponseEntity.ok(result)
                : ResponseEntity.badRequest().body(result);
    }

    
        @DeleteMapping("/drinks/{drinkId}")
    public ResponseEntity<?> deleteDrink(@PathVariable Integer drinkId) {
        try {
            menuService.deleteDrink(drinkId);
            return ResponseEntity.ok("Xóa món đồ uống thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }

}
