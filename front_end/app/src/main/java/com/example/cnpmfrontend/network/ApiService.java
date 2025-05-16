package com.example.cnpmfrontend.network;

import com.example.cnpmfrontend.model.DrinkCategory;
import com.example.cnpmfrontend.model.DrinkItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    // Đổi thành IP máy tính của bạn nếu chạy trên máy ảo Android
    // 10.0.2.2 là localhost của máy host khi chạy trên Android Emulator
    // Nếu chạy trên thiết bị thật, dùng IP của máy tính trong cùng mạng Wi-Fi
    String BASE_URL = "http://10.0.2.2:8080/api/menu/"; // Thay port nếu cần

    @GET("categories")
    Call<List<DrinkCategory>> getAllCategories();

    @GET("drinks")
    Call<List<DrinkItem>> getAllDrinks();

    @GET("drinks/category/{categoryId}")
    Call<List<DrinkItem>> getDrinksByCategory(@Path("categoryId") int categoryId);

    @GET("drinks/{drinkId}")
    Call<DrinkItem> getDrinkById(@Path("drinkId") int drinkId);
}
