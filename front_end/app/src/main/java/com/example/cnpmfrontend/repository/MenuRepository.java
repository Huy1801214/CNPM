package com.example.cnpmfrontend.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.network.ApiService;
import com.example.cnpmfrontend.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuRepository {
    private ApiService apiService;

    public MenuRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<DrinkItem>> getAllDrinks() {
        MutableLiveData<List<DrinkItem>> data = new MutableLiveData<>();
        apiService.getAllDrinks().enqueue(new Callback<List<DrinkItem>>() {
            @Override
            public void onResponse(Call<List<DrinkItem>> call, Response<List<DrinkItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null); // Hoặc xử lý lỗi cụ thể hơn
                }
            }

            @Override
            public void onFailure(Call<List<DrinkItem>> call, Throwable t) {
                data.setValue(null); // Hoặc xử lý lỗi cụ thể hơn
                // Log lỗi: Log.e("MenuRepository", "Failed to fetch drinks", t);
            }
        });
        return data;
    }

    // Tương tự cho getAllCategories, getDrinksByCategory, ...
    // Ví dụ:
    public LiveData<DrinkItem> getDrinkById(int drinkId) {
        MutableLiveData<DrinkItem> data = new MutableLiveData<>();
        apiService.getDrinkById(drinkId).enqueue(new Callback<DrinkItem>() {
            @Override
            public void onResponse(Call<DrinkItem> call, Response<DrinkItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<DrinkItem> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
