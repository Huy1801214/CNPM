package com.example.cnpmfrontend;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cnpmfrontend.adapter.DrinkAdapter;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.viewmodel.MenuViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MenuViewModel menuViewModel;
    private DrinkAdapter drinkAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMenu);
        progressBar = findViewById(R.id.progressBar);
        textViewError = findViewById(R.id.textViewError);

        // Khởi tạo ViewModel
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        setupRecyclerView();
        observeViewModel();

        // Gọi API để lấy dữ liệu
        // Trong phiên bản này, fetchAllDrinks() trả về LiveData, chúng ta observe nó
        menuViewModel.fetchAllDrinks().observe(this, new Observer<List<DrinkItem>>() {
            @Override
            public void onChanged(List<DrinkItem> drinks) {
                if (drinks != null && !drinks.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewError.setVisibility(View.GONE);
                    drinkAdapter.setDrinks(drinks);
                } else if (menuViewModel.getErrorMessage().getValue() == null) { // drinks null nhưng không có lỗi cụ thể
                    recyclerView.setVisibility(View.GONE);
                    textViewError.setText("Không có đồ uống nào.");
                    textViewError.setVisibility(View.VISIBLE);
                }
                // Nếu drinks null và có lỗi, nó sẽ được xử lý bởi observeErrorMessage
            }
        });
    }

    private void setupRecyclerView() {
        drinkAdapter = new DrinkAdapter(this, new ArrayList<>()); // Khởi tạo với list rỗng
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drinkAdapter);
    }

    private void observeViewModel() {
        menuViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
            }
        });

        menuViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    textViewError.setText(errorMessage);
                    textViewError.setVisibility(View.VISIBLE);
                    // Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    // Nếu không có lỗi và có dữ liệu thì ẩn text view lỗi
                    // if (drinkAdapter.getItemCount() > 0) {
                    //      textViewError.setVisibility(View.GONE);
                    // }
                }
            }
        });
    }
}
