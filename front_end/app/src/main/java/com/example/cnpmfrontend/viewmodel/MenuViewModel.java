package com.example.cnpmfrontend.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cnpmfrontend.model.DrinkCategory;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.model.DrinkItemRequest;
import com.example.cnpmfrontend.repository.MenuRepository;

import java.util.List;

public class MenuViewModel extends ViewModel {
    private MenuRepository menuRepository = new MenuRepository();

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private LiveData<List<DrinkItem>> allDrinksLiveData;
    private LiveData<List<DrinkCategory>> allCategoriesLiveData;

    public LiveData<List<DrinkItem>> getAllDrinks() {
        allDrinksLiveData = menuRepository.getAllDrinks(errorMessage, isLoading);
        return allDrinksLiveData;
    }

    public LiveData<List<DrinkCategory>> getAllCategories() {
        allCategoriesLiveData = menuRepository.getAllCategories(errorMessage, isLoading);
        return allCategoriesLiveData;
    }

    public LiveData<String> addDrinkItem(DrinkItemRequest request) {
        return menuRepository.addDrinkItem(request, isLoading, errorMessage);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
