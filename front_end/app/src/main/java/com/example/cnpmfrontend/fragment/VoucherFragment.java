package com.example.cnpmfrontend.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.adapter.VoucherAdapter;
import com.example.cnpmfrontend.model.Voucher;
import com.example.cnpmfrontend.fragment.AddVoucherActivity;
import com.example.cnpmfrontend.viewmodel.VoucherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VoucherFragment extends Fragment {

    private static final String TAG = "VoucherFragment";
    private VoucherViewModel voucherViewModel;
    private VoucherAdapter voucherAdapter;
    private RecyclerView recyclerViewVouchers;
    private ProgressBar progressBarVouchers;
    private TextView textViewVoucherError;
    private FloatingActionButton fabAddVoucher;
    private ActivityResultLauncher<Intent> addVoucherLauncher;

    public VoucherFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        voucherViewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
        addVoucherLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 3. Xử lý kết quả ở đây (thay thế cho onActivityResult cũ)
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // AddVoucherActivity đã đóng và báo hiệu thành công
                            // Không cần kiểm tra requestCode nữa
                            Log.d(TAG, "AddVoucherActivity returned RESULT_OK (New API), refreshing vouchers...");
                            if (voucherViewModel != null) {
                                voucherViewModel.fetchAllVouchers();
                            }
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);
        recyclerViewVouchers = view.findViewById(R.id.recyclerViewVouchers);
        progressBarVouchers = view.findViewById(R.id.progressBarVouchers);
        textViewVoucherError = view.findViewById(R.id.textViewVoucherError);
        fabAddVoucher = view.findViewById(R.id.fab_add_voucher);

        setupRecyclerView();
        // Huy - add voucher
        // 5.1.1. bắt sự kiện onClick(), tạo một Intent để khởi chạy AddVoucherActivity
        fabAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVoucherActivity.class);
                addVoucherLauncher.launch(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
        if (voucherViewModel.getAllVouchers().getValue() == null) {
            voucherViewModel.fetchAllVouchers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupRecyclerView() {
        voucherAdapter = new VoucherAdapter(getContext(), new ArrayList<>());
        recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewVouchers.setAdapter(voucherAdapter);
    }

    private void observeViewModel() {
        voucherViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBarVouchers.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                textViewVoucherError.setVisibility(View.GONE);
                recyclerViewVouchers.setVisibility(View.GONE);
            }
        });

        voucherViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Log.e(TAG, "Error: " + errorMessage);
                recyclerViewVouchers.setVisibility(View.GONE);
                textViewVoucherError.setText(errorMessage);
                textViewVoucherError.setVisibility(View.VISIBLE);
            }
        });

        voucherViewModel.getAllVouchers().observe(getViewLifecycleOwner(), vouchers -> {
            Boolean isLoading = voucherViewModel.getIsLoading().getValue();
            String currentError = voucherViewModel.getErrorMessage().getValue();

            if ((isLoading == null || !isLoading)) {
                if (vouchers != null && !vouchers.isEmpty()) {
                    recyclerViewVouchers.setVisibility(View.VISIBLE);
                    textViewVoucherError.setVisibility(View.GONE);
                    voucherAdapter.setVouchers(vouchers);
                } else if (vouchers != null && vouchers.isEmpty() && (currentError == null || currentError.isEmpty())) {
                    recyclerViewVouchers.setVisibility(View.GONE);
                    textViewVoucherError.setText("Không có ưu đãi nào.");
                    textViewVoucherError.setVisibility(View.VISIBLE);
                } else if (vouchers == null && (currentError == null || currentError.isEmpty())) {
                    if (!(isLoading != null && isLoading)) {
                        recyclerViewVouchers.setVisibility(View.GONE);
                        textViewVoucherError.setText("Không có ưu đãi nào.");
                        textViewVoucherError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}