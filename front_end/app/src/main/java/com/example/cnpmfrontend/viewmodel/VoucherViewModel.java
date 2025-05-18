package com.example.cnpmfrontend.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.cnpmfrontend.model.Voucher;
import com.example.cnpmfrontend.repository.VoucherRepository;
import java.util.List;

public class VoucherViewModel extends ViewModel {
    private VoucherRepository voucherRepository;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<Boolean> createVoucherSuccess = new MutableLiveData<>();

    private LiveData<List<Voucher>> allVouchersLiveData;

    public VoucherViewModel() {
        voucherRepository = new VoucherRepository();
    }

    public LiveData<List<Voucher>> getAllVouchers() {
        allVouchersLiveData = voucherRepository.getAllVouchers(errorMessage, isLoading);
        return allVouchersLiveData;
    }

    public void fetchAllVouchers() {
        allVouchersLiveData = voucherRepository.getAllVouchers(errorMessage, isLoading);
    }


    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void createVoucher(Voucher voucher) {
        voucherRepository.createVoucher(voucher, errorMessage, isLoading).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                createVoucherSuccess.setValue(success);
            }
        });
    }

    public LiveData<Boolean> getCreateVoucherSuccess() {
        return createVoucherSuccess;
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }
}