package com.example.cnpmbackend.services;

import com.example.cnpmbackend.dto.*;
import com.example.cnpmbackend.entity.DrinkCategory;
import com.example.cnpmbackend.entity.DrinkItem;
import com.example.cnpmbackend.entity.Topping;
import com.example.cnpmbackend.responsitory.DrinkCategoryRepository;
import com.example.cnpmbackend.responsitory.DrinkItemRepository;
import com.example.cnpmbackend.responsitory.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private DrinkCategoryRepository categoryRepository;

    @Autowired
    private DrinkItemRepository drinkItemRepository;

    @Autowired
    private ToppingRepository toppingRepository;

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
    public String addDrink(DrinkItemRequestDTO dto) {
        if (drinkItemRepository.existsByNameIgnoreCase(dto.getName())) {
            return "Tên bị trùng";
        }

        DrinkCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        Set<Topping> toppings = toppingRepository.findAllById(dto.getToppingIds())
                .stream().collect(Collectors.toSet());

        DrinkItem item = new DrinkItem();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setImageUrl(dto.getImageUrl());
        item.setAvailable(dto.getAvailable());
        item.setCategory(category);
        item.setToppings(toppings);

        drinkItemRepository.save(item);
        return "Thêm thành công";
    }
}
