package com.example.cnpmfrontend.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.repository.MenuRepository;
import java.util.List;

public class MenuViewModel extends ViewModel {
    private MenuRepository menuRepository;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private LiveData<List<DrinkItem>> allDrinksLiveData;

    public MenuViewModel() {
        menuRepository = new MenuRepository();
    }

    public LiveData<List<DrinkItem>> getAllDrinks() {
        allDrinksLiveData = menuRepository.getAllDrinks(errorMessage, isLoading);
        return allDrinksLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}