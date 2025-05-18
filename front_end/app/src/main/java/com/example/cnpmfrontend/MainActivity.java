package com.example.cnpmfrontend;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.cnpmfrontend.fragment.MenuFragment;
import com.example.cnpmfrontend.fragment.VoucherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Load fragment mặc định khi Activity được tạo lần đầu
        if (savedInstanceState == null) {
            loadFragment(new MenuFragment(), "MENU_FRAGMENT_TAG");
            bottomNavigationView.setSelectedItemId(R.id.navigation_menu);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                String tag = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_menu) {
                    selectedFragment = new MenuFragment();
                    tag = "MENU_FRAGMENT_TAG";
                } else if (itemId == R.id.navigation_vouchers) {
                    selectedFragment = new VoucherFragment();
                    tag = "VOUCHER_FRAGMENT_TAG";
                }
                if (selectedFragment != null) {
                    loadFragment(selectedFragment, tag);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}