package com.example.cnpmfrontend.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cnpmfrontend.model.DrinkCategory;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.network.ApiService;
import com.example.cnpmfrontend.network.RetrofitClient;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuRepository {
    private static final String TAG = "MenuRepository";
    private ApiService apiService;

    public MenuRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<DrinkItem>> getAllDrinks(MutableLiveData<String> errorMessageLiveData, MutableLiveData<Boolean> isLoadingLiveData) {
        isLoadingLiveData.setValue(true);
        MutableLiveData<List<DrinkItem>> drinksData = new MutableLiveData<>();

        apiService.getAllDrinks().enqueue(new Callback<List<DrinkItem>>() {
            @Override
            public void onResponse(Call<List<DrinkItem>> call, Response<List<DrinkItem>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    drinksData.setValue(response.body());
                    errorMessageLiveData.setValue(null); // Clear any previous error
                } else {
                    handleApiError(response, drinksData, errorMessageLiveData, "lấy danh sách đồ uống");
                }
            }

            @Override
            public void onFailure(Call<List<DrinkItem>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                drinksData.setValue(null);
                String errorMsg = "Lỗi mạng khi lấy danh sách đồ uống: " + t.getMessage();
                if (t instanceof java.net.ConnectException) {
                    errorMsg = "Không thể kết nối đến server. Vui lòng kiểm tra IP/Port và kết nối mạng.";
                }
                errorMessageLiveData.setValue(errorMsg);
                Log.e(TAG, "getAllDrinks onFailure: " + errorMsg, t);
            }
        });
        return drinksData;
    }

    // Bạn có thể thêm các phương thức khác cho categories, getDrinkById tương tự

    private <T> void handleApiError(Response<T> response, MutableLiveData<T> data, MutableLiveData<String> errorMessage, String actionDescription) {
        data.setValue(null);
        String errorMsg = "Lỗi không xác định khi " + actionDescription + ".";
        if (response.errorBody() != null) {
            try {
                String errorBodyStr = response.errorBody().string(); // Đọc errorBody một lần
                errorMsg = "Lỗi " + response.code() + " khi " + actionDescription + ": " + errorBodyStr;
                Log.e(TAG, "API Error " + response.code() + " (" + actionDescription + "): " + errorBodyStr);
            } catch (IOException e) {
                Log.e(TAG, "Error reading errorBody for " + actionDescription, e);
                errorMsg = "Lỗi " + response.code() + " khi " + actionDescription + " (không thể đọc chi tiết lỗi).";
            }
        } else {
            errorMsg = "Lỗi " + response.code() + " khi " + actionDescription + " (không có error body).";
            Log.e(TAG, "API Error " + response.code() + " (" + actionDescription + ") with no error body.");
        }
        errorMessage.setValue(errorMsg);
    }
}