package com.example.cnpmbackend.services;

import com.example.cnpmbackend.entity.DrinkCategory;
import com.example.cnpmbackend.entity.DrinkItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.cnpmbackend.responsitory.DrinkCategoryRepository;
import com.example.cnpmbackend.responsitory.DrinkItemRepository;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private DrinkCategoryRepository categoryRepository;

    @Autowired
    private DrinkItemRepository drinkItemRepository;

    public List<DrinkCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<DrinkItem> getDrinksByCategoryId(Integer categoryId) {
        return drinkItemRepository.findByCategory_Id(categoryId);
    }

    public List<DrinkItem> getAllDrinks() {
        return drinkItemRepository.findAll();
    }

    public DrinkItem getDrinkById(Integer drinkId) {
        return drinkItemRepository.findById(drinkId).orElse(null);
    }
}
