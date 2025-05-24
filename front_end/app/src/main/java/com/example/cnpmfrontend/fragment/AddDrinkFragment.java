package com.example.cnpmfrontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.model.DrinkItemRequest;
import com.example.cnpmfrontend.viewmodel.MenuViewModel;

import java.util.HashSet;
import java.util.Set;

public class AddDrinkFragment extends Fragment {

    private EditText edtName, edtDesc, edtPrice, edtImageUrl, edtCategoryId, edtToppings;
    private Switch switchAvailable;
    private Button btnAdd;
    private MenuViewModel menuViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_menu, container, false);

        edtName = view.findViewById(R.id.edtName);
        edtDesc = view.findViewById(R.id.edtDesc);
        edtPrice = view.findViewById(R.id.edtPrice);
        edtImageUrl = view.findViewById(R.id.edtImageUrl);
        edtCategoryId = view.findViewById(R.id.edtCategoryId);
        edtToppings = view.findViewById(R.id.edtToppings);
        switchAvailable = view.findViewById(R.id.switchAvailable);
        btnAdd = view.findViewById(R.id.btnAddDrink);

        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);

        btnAdd.setOnClickListener(v -> {
            DrinkItemRequest item = new DrinkItemRequest();
            item.setName(edtName.getText().toString());
            item.setDescription(edtDesc.getText().toString());
            item.setPrice(Double.parseDouble(edtPrice.getText().toString()));
            item.setImageUrl(edtImageUrl.getText().toString());
            item.setAvailable(switchAvailable.isChecked());
            item.setCategoryId(Integer.parseInt(edtCategoryId.getText().toString()));

            String[] toppingIdsStr = edtToppings.getText().toString().split(",");
            Set<Integer> toppingIds = new HashSet<>();
            for (String s : toppingIdsStr) {
                try {
                    toppingIds.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException ignored) {}
            }
            item.setToppingIds(toppingIds);

            menuViewModel.addDrinkItem(item).observe(getViewLifecycleOwner(), result -> {
                if (result != null && result.toLowerCase().contains("thành công")) {
                    Toast.makeText(getContext(), "Thêm món thành công!", Toast.LENGTH_SHORT).show();

                    // Gửi thông báo cho MenuFragment biết đã thêm món thành công
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("drink_added", true);
                    getParentFragmentManager().setFragmentResult("requestKey", bundle);

                    // Quay lại fragment thực đơn
                    getParentFragmentManager().popBackStack();
                } else if (result != null && result.toLowerCase().contains("tên bị trùng")) {
                    Toast.makeText(getContext(), "Lỗi: Tên món đã tồn tại, vui lòng đổi tên khác!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Lỗi khi thêm món: " + result, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

}
