package com.example.cnpmfrontend;

import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "MainActivity";
    private MenuViewModel menuViewModel;
    private DrinkAdapter drinkAdapter;
    private RecyclerView recyclerViewMenu;
    private ProgressBar progressBar;
    private TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        progressBar = findViewById(R.id.progressBar);
        textViewError = findViewById(R.id.textViewError);

        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        setupRecyclerView();
        observeViewModel();

        // Yêu cầu ViewModel tải dữ liệu đồ uống
        menuViewModel.getAllDrinks(); // ViewModel sẽ kích hoạt việc gọi API
    }

    private void setupRecyclerView() {
        // Khởi tạo adapter với một danh sách rỗng ban đầu
        drinkAdapter = new DrinkAdapter(this, new ArrayList<>());
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenu.setAdapter(drinkAdapter);
    }

    private void observeViewModel() {
        // Observe trạng thái loading
        menuViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    textViewError.setVisibility(View.GONE);
                    recyclerViewMenu.setVisibility(View.GONE);
                }
            }
        });

        // Observe thông báo lỗi
        menuViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Log.e(TAG, "Error from ViewModel: " + errorMessage);
                recyclerViewMenu.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textViewError.setText(errorMessage);
                textViewError.setVisibility(View.VISIBLE);
                // Bạn có thể hiển thị Toast ở đây nếu muốn
                // Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            } else {
                // Nếu không có lỗi, và đã có dữ liệu thì ẩn textViewError
                // (Logic này đã được xử lý trong drinks observer)
            }
        });

        // Observe danh sách đồ uống từ ViewModel
        menuViewModel.getAllDrinks().observe(this, drinks -> {
            // Chỉ cập nhật UI nếu không đang loading và không có lỗi nào đang được hiển thị
            Boolean isLoading = menuViewModel.getIsLoading().getValue();
            String currentError = menuViewModel.getErrorMessage().getValue();

            if ((isLoading == null || !isLoading)) { // Bỏ qua kiểm tra lỗi ở đây, để error observer xử lý
                if (drinks != null && !drinks.isEmpty()) {
                    Log.d(TAG, "Drinks data received for UI. Size: " + drinks.size());
                    recyclerViewMenu.setVisibility(View.VISIBLE);
                    textViewError.setVisibility(View.GONE);
                    drinkAdapter.setDrinks(drinks);
                } else if (drinks != null && drinks.isEmpty() && (currentError == null || currentError.isEmpty())) {
                    // Danh sách rỗng và không có lỗi nào từ API
                    Log.d(TAG, "Received empty drinks list and no API error.");
                    recyclerViewMenu.setVisibility(View.GONE);
                    textViewError.setText("Không có đồ uống nào để hiển thị.");
                    textViewError.setVisibility(View.VISIBLE);
                } else if (drinks == null && (currentError == null || currentError.isEmpty())) {
                    // Dữ liệu null và không có lỗi nào từ API (có thể là trạng thái ban đầu)
                    // Để observer lỗi xử lý nếu có lỗi thực sự
                    Log.d(TAG, "Drinks list is null, no API error yet.");
                    if (! (isLoading != null && isLoading) ) { // Chỉ hiển thị "Không có đồ uống" nếu không đang loading
                        recyclerViewMenu.setVisibility(View.GONE);
                        textViewError.setText("Không có đồ uống nào.");
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }
                // Nếu drinks là null VÀ currentError có giá trị, thì errorMessage observer sẽ xử lý
            }
        });
    }
}