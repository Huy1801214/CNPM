package com.example.cnpmbackend.services;

import com.example.cnpmbackend.dto.DrinkCategoryDTO;
import com.example.cnpmbackend.dto.DrinkItemDTO;
import com.example.cnpmbackend.dto.SimpleCategoryDTO;
import com.example.cnpmbackend.dto.SimpleToppingDTO;
import com.example.cnpmbackend.entity.DrinkCategory;
import com.example.cnpmbackend.entity.DrinkItem;
import com.example.cnpmbackend.entity.Topping;
import com.example.cnpmbackend.responsitory.DrinkCategoryRepository; // Sửa tên package nếu cần
import com.example.cnpmbackend.responsitory.DrinkItemRepository;   // Sửa tên package nếu cần
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Quan trọng cho LAZY loading

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private DrinkCategoryRepository categoryRepository;

    @Autowired
    private DrinkItemRepository drinkItemRepository;

    private SimpleToppingDTO mapToSimpleToppingDTO(Topping topping) {
        if (topping == null) return null;
        return new SimpleToppingDTO(
                topping.getId(),
                topping.getName(),
                topping.getPrice()
        );
    }

    private SimpleCategoryDTO mapToSimpleCategoryDTO(DrinkCategory category) {
        if (category == null) return null;
        return new SimpleCategoryDTO(
                category.getId(),
                category.getName()
        );
    }

    private DrinkItemDTO mapToDrinkItemDTO(DrinkItem drinkItem) {
        if (drinkItem == null) return null;

        Set<SimpleToppingDTO> toppingDTOs = null;
        if (drinkItem.getToppings() != null) {
            toppingDTOs = drinkItem.getToppings().stream()
                    .map(this::mapToSimpleToppingDTO)
                    .collect(Collectors.toSet());
        }

        return new DrinkItemDTO(
                drinkItem.getId(),
                drinkItem.getName(),
                drinkItem.getDescription(),
                drinkItem.getPrice(),
                drinkItem.getImageUrl(),
                mapToSimpleCategoryDTO(drinkItem.getCategory()),
                drinkItem.getAvailable(),
                toppingDTOs
        );
    }

    private DrinkCategoryDTO mapToDrinkCategoryDTO(DrinkCategory category) {
        if (category == null) return null;
        return new DrinkCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }


    @Transactional(readOnly = true)
    public List<DrinkCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDrinkCategoryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DrinkItemDTO> getDrinksByCategoryId(Integer categoryId) {
        return drinkItemRepository.findByCategory_Id(categoryId)
                .stream()
                .map(this::mapToDrinkItemDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DrinkItemDTO> getAllDrinks() {
        List<DrinkItem> drinks = drinkItemRepository.findAll();
        System.out.println("độ lớn của drinks (service entities): " + drinks.size());
        List<DrinkItemDTO> drinkDTOs = drinks.stream()
                .map(this::mapToDrinkItemDTO)
                .collect(Collectors.toList());
        System.out.println("độ lớn của drinkDTOs (service DTOs): " + drinkDTOs.size());
        return drinkDTOs;
    }

    @Transactional(readOnly = true)
    public DrinkItemDTO getDrinkById(Integer drinkId) {
        return drinkItemRepository.findById(drinkId)
                .map(this::mapToDrinkItemDTO)
                .orElse(null);
    }
    @Transactional
    public void deleteDrink(Integer drinkId) {
        // Kiểm tra xem món có tồn tại không
        DrinkItem drinkItem = drinkItemRepository.findById(drinkId)
                .orElseThrow(() -> new IllegalArgumentException("Món đồ uống với ID " + drinkId + " không tồn tại."));

        // Xóa món đồ uống (các liên kết trong drink_toppings sẽ tự động được xóa do CascadeType.ALL)
        drinkItemRepository.delete(drinkItem);
    }
}