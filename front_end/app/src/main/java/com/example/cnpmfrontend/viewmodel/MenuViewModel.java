package com.example.cnpmfrontend.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel; // Hoặc ViewModel nếu không cần context
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.network.RetrofitClient;
import com.example.cnpmfrontend.repository.MenuRepository;
import java.util.List;

import retrofit2.Call;

public class MenuViewModel extends AndroidViewModel { // Hoặc ViewModel
    private MenuRepository repository;
    private LiveData<List<DrinkItem>> allDrinks;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();


    // Constructor cho ViewModel
    public MenuViewModel(@NonNull Application application) {
        super(application);
        repository = new MenuRepository();
        // Thay vì gọi fetch ở đây, chúng ta sẽ gọi từ Activity/Fragment
        // và repository sẽ trả về LiveData
    }

    // Hoặc nếu không cần Application context:
    // public MenuViewModel() {
    //     repository = new MenuRepository();
    // }

    public LiveData<List<DrinkItem>> fetchAllDrinks() {
        isLoading.setValue(true);
        // Trong phiên bản này, Repository trả về LiveData trực tiếp
        // Chúng ta có thể muốn có một LiveData riêng trong ViewModel để quản lý trạng thái loading/error
        // Hoặc là có một method trong repository trả về một loại Result wrapper.
        // Để đơn giản, chúng ta sẽ lấy LiveData từ repo và observe nó.
        // Tuy nhiên, cách tiếp cận tốt hơn là repository trả về dữ liệu thô
        // và ViewModel quản lý trạng thái.
        //
        // Cách tiếp cận đơn giản:
        // allDrinks = repository.getAllDrinks(); // Đây là cách gọi API mỗi lần
        // return allDrinks;

        // Cách tiếp cận tốt hơn một chút để quản lý loading/error:
        MutableLiveData<List<DrinkItem>> drinksLiveData = new MutableLiveData<>();
        isLoading.setValue(true);
        errorMessage.setValue(null);

        RetrofitClient.getApiService().getAllDrinks().enqueue(new retrofit2.Callback<List<DrinkItem>>() {
            @Override
            public void onResponse(Call<List<DrinkItem>> call, retrofit2.Response<List<DrinkItem>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    drinksLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue("Lỗi tải dữ liệu: " + response.code());
                    drinksLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<DrinkItem>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi mạng: " + t.getMessage());
                drinksLiveData.setValue(null);
            }
        });
        return drinksLiveData;
    }


    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Nếu bạn muốn cache dữ liệu, bạn sẽ làm khác một chút
    // public LiveData<List<DrinkItem>> getAllDrinks() {
    //     if (allDrinks == null) {
    //         allDrinks = new MutableLiveData<>(); // Khởi tạo nếu null
    //         loadDrinks(); // Gọi phương thức để tải dữ liệu
    //     }
    //     return allDrinks;
    // }
    //
    // private void loadDrinks() {
    //     isLoading.setValue(true);
    //     // Gọi API từ repository...
    // }
}
