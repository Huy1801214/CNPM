package com.example.cnpmbackend.controllers;

import com.example.cnpmbackend.dto.DrinkCategoryDTO; // Import DTO
import com.example.cnpmbackend.dto.DrinkItemDTO;   // Import DTO
import com.example.cnpmbackend.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    //Chức năng xóa đồ uống (Bảo)
    @DeleteMapping("/drinks/{drinkId}")
    public ResponseEntity<?> deleteDrinkById(@PathVariable Integer drinkId) {
        try {
            // Kiểm tra xem đồ uống có tồn tại không
            boolean deleted = menuService.deleteDrinkById(drinkId);
            if (deleted) {
                // Nếu xóa thành công, trả về mã 204 No Content
                return ResponseEntity.noContent().build();
            } else {
                // Nếu không tìm thấy đồ uống với ID đã cho, trả về mã 404 Not Found
                return ResponseEntity.status(404).body("Không tìm thấy đồ uống với ID: " + drinkId);
            }
        } catch (Exception e) {
            // Nếu có lỗi xảy ra trong quá trình xóa, trả về mã 500 Internal Server Error
            return ResponseEntity.status(500).body("Lỗi hệ thống khi xóa đồ uống: " + e.getMessage());
        }
    }
}