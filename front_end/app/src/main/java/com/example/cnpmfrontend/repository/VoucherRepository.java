package com.example.cnpmfrontend.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cnpmfrontend.model.Voucher;
import com.example.cnpmfrontend.network.ApiService;
import com.example.cnpmfrontend.network.RetrofitClient;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherRepository {
    private static final String TAG = "VoucherRepository";
    private ApiService apiService;

    public VoucherRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Voucher>> getAllVouchers(MutableLiveData<String> errorMessageLiveData, MutableLiveData<Boolean> isLoadingLiveData) {
        isLoadingLiveData.setValue(true);
        MutableLiveData<List<Voucher>> vouchersData = new MutableLiveData<>();

        apiService.getAllVouchers().enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    vouchersData.setValue(response.body());
                    errorMessageLiveData.setValue(null);
                } else {
                    handleApiError(response, vouchersData, errorMessageLiveData, "lấy danh sách voucher");
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                vouchersData.setValue(null);
                String errorMsg = "Lỗi mạng khi lấy danh sách voucher: " + t.getMessage();
                if (t instanceof java.net.ConnectException) {
                    errorMsg = "Không thể kết nối đến server (voucher).";
                }
                errorMessageLiveData.setValue(errorMsg);
                Log.e(TAG, "getAllVouchers onFailure: " + errorMsg, t);
            }
        });
        return vouchersData;
    }

    private <T> void handleApiError(Response<T> response, MutableLiveData<T> data, MutableLiveData<String> errorMessage, String actionDescription) {
        data.setValue(null);
        String errorMsg = "Lỗi không xác định khi " + actionDescription + ".";
        if (response.errorBody() != null) {
            try {
                String errorBodyStr = response.errorBody().string();
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

    public LiveData<Boolean> createVoucher(Voucher voucher, MutableLiveData<String> errorMessageLiveData, MutableLiveData<Boolean> isLoadingLiveData) {
        isLoadingLiveData.setValue(true);
        MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

        apiService.createVoucher(voucher).enqueue(new Callback<Voucher>() {
            @Override
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                isLoadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    successLiveData.setValue(true);
                    errorMessageLiveData.setValue(null); // Xóa lỗi cũ
                    Log.d(TAG, "Voucher created successfully: " + response.body().getCode());
                } else {
                    successLiveData.setValue(false);
                    handleApiError(response, null, errorMessageLiveData, "tạo voucher");
                }
            }

            @Override
            public void onFailure(Call<Voucher> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                successLiveData.setValue(false);
                String errorMsg = "Lỗi mạng khi tạo voucher: " + t.getMessage();
                if (t instanceof java.net.ConnectException) {
                    errorMsg = "Không thể kết nối đến server (tạo voucher).";
                }
                errorMessageLiveData.setValue(errorMsg);
                Log.e(TAG, "createVoucher onFailure: " + errorMsg, t);
            }
        });
        return successLiveData;
    }
}