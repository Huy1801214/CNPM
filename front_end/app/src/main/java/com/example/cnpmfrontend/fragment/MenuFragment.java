package com.example.cnpmfrontend.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.adapter.DrinkAdapter;
import com.example.cnpmfrontend.viewmodel.MenuViewModel; // Đảm bảo đúng ViewModel
import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";
    private MenuViewModel menuViewModel;
    private DrinkAdapter drinkAdapter;
    private RecyclerView recyclerViewMenu;
    private ProgressBar progressBar;
    private TextView textViewError;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo ViewModel, sử dụng activity's scope nếu muốn chia sẻ ViewModel
        // hoặc fragment's scope nếu chỉ dùng trong fragment này.
        // Dùng requireActivity() để ViewModel tồn tại cùng Activity chứa nó.
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerViewMenu = view.findViewById(R.id.recyclerViewMenu);
        progressBar = view.findViewById(R.id.progressBar);
        textViewError = view.findViewById(R.id.textViewError);

        setupRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewModel();
        // Yêu cầu ViewModel tải dữ liệu nếu chưa có hoặc cần refresh
        // ViewModel sẽ quản lý việc gọi repository
        // Việc gọi này nên chỉ xảy ra một lần hoặc khi cần refresh
        if (menuViewModel.getAllDrinks().getValue() == null) { // Chỉ fetch nếu chưa có dữ liệu
            menuViewModel.getAllDrinks();
        }
    }

    private void setupRecyclerView() {
        drinkAdapter = new DrinkAdapter(getContext(), new ArrayList<>());
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMenu.setAdapter(drinkAdapter);
    }

    private void observeViewModel() {
        menuViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    textViewError.setVisibility(View.GONE);
                    recyclerViewMenu.setVisibility(View.GONE);
                }
            }
        });

        menuViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Log.e(TAG, "Error from ViewModel: " + errorMessage);
                recyclerViewMenu.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textViewError.setText(errorMessage);
                textViewError.setVisibility(View.VISIBLE);
            }
        });

        menuViewModel.getAllDrinks().observe(getViewLifecycleOwner(), drinks -> {
            Boolean isLoading = menuViewModel.getIsLoading().getValue();
            String currentError = menuViewModel.getErrorMessage().getValue();

            if ((isLoading == null || !isLoading)) {
                if (drinks != null && !drinks.isEmpty()) {
                    Log.d(TAG, "Drinks data received for UI. Size: " + drinks.size());
                    recyclerViewMenu.setVisibility(View.VISIBLE);
                    textViewError.setVisibility(View.GONE);
                    drinkAdapter.setDrinks(drinks);
                } else if (drinks != null && drinks.isEmpty() && (currentError == null || currentError.isEmpty())) {
                    Log.d(TAG, "Received empty drinks list and no API error.");
                    recyclerViewMenu.setVisibility(View.GONE);
                    textViewError.setText("Không có đồ uống nào để hiển thị.");
                    textViewError.setVisibility(View.VISIBLE);
                } else if (drinks == null && (currentError == null || currentError.isEmpty())) {
                    Log.d(TAG, "Drinks list is null, no API error yet.");
                    if (! (isLoading != null && isLoading) ) {
                        recyclerViewMenu.setVisibility(View.GONE);
                        textViewError.setText("Không có đồ uống nào.");
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}