package com.example.cnpmfrontend.network;

import com.example.cnpmfrontend.model.DrinkCategory;
import com.example.cnpmfrontend.model.DrinkItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.cnpmfrontend.model.Voucher;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    // QUAN TRỌNG: Đảm bảo đây là IP máy tính của bạn nếu chạy trên thiết bị thật,
    // hoặc 10.0.2.2 nếu chạy trên Android Emulator.
    // Và port phải đúng với backend Spring Boot của bạn (mặc định 8080).
    // BASE_URL nên trỏ đến thư mục gốc của API, ví dụ: "http://10.0.2.2:8080/api/"
//    String BASE_URL = "http://192.168.100.200:8080/api/";
    String BASE_URL = "http://10.0.2.2:8080/api/"; // Sửa nếu cần

    @GET("menu/categories")
    Call<List<DrinkCategory>> getAllCategories();

    @GET("menu/drinks")
    Call<List<DrinkItem>> getAllDrinks();

    @GET("menu/drinks/category/{categoryId}")
    Call<List<DrinkItem>> getDrinksByCategory(@Path("categoryId") int categoryId);

    @GET("menu/drinks/{drinkId}")
    Call<DrinkItem> getDrinkById(@Path("drinkId") int drinkId);

    @GET("vouchers")
    Call<List<Voucher>> getAllVouchers();

    @POST("vouchers")
    Call<Voucher> createVoucher(@Body Voucher voucher);
}