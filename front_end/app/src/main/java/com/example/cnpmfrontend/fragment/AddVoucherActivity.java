package com.example.cnpmfrontend.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.model.Voucher; // Android Model
import com.example.cnpmfrontend.viewmodel.VoucherViewModel; // ViewModel cho Voucher
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddVoucherActivity extends AppCompatActivity {

    private static final String TAG = "AddVoucherActivity";

    private TextInputEditText editTextVoucherCode, editTextVoucherDescription, editTextDiscountValue, editTextMaxUses;
    private Spinner spinnerDiscountType;
    private EditText editTextValidFrom, editTextValidTo;
    private CheckBox checkBoxIsActive;
    private Button buttonAddVoucher;
    private ProgressBar progressBarAddVoucher;

    private VoucherViewModel voucherViewModel;
    private Calendar calendarValidFrom = Calendar.getInstance();
    private Calendar calendarValidTo = Calendar.getInstance();
    private SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_voucher);

        editTextVoucherCode = findViewById(R.id.editTextVoucherCode);
        editTextVoucherDescription = findViewById(R.id.editTextVoucherDescription);
        editTextDiscountValue = findViewById(R.id.editTextDiscountValue);
        spinnerDiscountType = findViewById(R.id.spinnerDiscountType);
        editTextMaxUses = findViewById(R.id.editTextMaxUses);
        editTextValidFrom = findViewById(R.id.editTextValidFrom);
        editTextValidTo = findViewById(R.id.editTextValidTo);
        checkBoxIsActive = findViewById(R.id.checkBoxIsActive);
        buttonAddVoucher = findViewById(R.id.buttonAddVoucher);
        progressBarAddVoucher = findViewById(R.id.progressBarAddVoucher);

        voucherViewModel = new ViewModelProvider(this).get(VoucherViewModel.class);

        setupSpinner();
        setupDatePickers();

        buttonAddVoucher.setOnClickListener(v -> attemptAddVoucher());

        observeViewModel();
    }

    private void setupSpinner() {
        String[] discountTypes = {"percentage", "fixed_amount"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, discountTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiscountType.setAdapter(adapter);
    }

    private void setupDatePickers() {
        editTextValidFrom.setOnClickListener(v -> showDateTimePicker(calendarValidFrom, editTextValidFrom));
        editTextValidTo.setOnClickListener(v -> showDateTimePicker(calendarValidTo, editTextValidTo));
        updateEditTextWithDateTime(calendarValidFrom, editTextValidFrom);
        calendarValidTo.add(Calendar.DAY_OF_MONTH, 1);
        updateEditTextWithDateTime(calendarValidTo, editTextValidTo);
    }

    private void showDateTimePicker(Calendar calendarInstance, EditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendarInstance.set(Calendar.YEAR, year);
            calendarInstance.set(Calendar.MONTH, month);
            calendarInstance.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (timeView, hourOfDay, minute) -> {
                calendarInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarInstance.set(Calendar.MINUTE, minute);
                calendarInstance.set(Calendar.SECOND, 0); // Set giây = 0
                updateEditTextWithDateTime(calendarInstance, editText);
            };
            new TimePickerDialog(AddVoucherActivity.this, timeSetListener,
                    calendarInstance.get(Calendar.HOUR_OF_DAY), calendarInstance.get(Calendar.MINUTE), true).show();
        };
        new DatePickerDialog(AddVoucherActivity.this, dateSetListener,
                calendarInstance.get(Calendar.YEAR), calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateEditTextWithDateTime(Calendar calendar, EditText editText) {
        editText.setText(isoFormat.format(calendar.getTime()));
    }


    private void attemptAddVoucher() {
        // Validate input
        String code = editTextVoucherCode.getText().toString().trim();
        String description = editTextVoucherDescription.getText().toString().trim();
        String discountStr = editTextDiscountValue.getText().toString().trim();
        String maxUsesStr = editTextMaxUses.getText().toString().trim();
        String validFromStr = editTextValidFrom.getText().toString().trim();
        String validToStr = editTextValidTo.getText().toString().trim();
        String discountType = spinnerDiscountType.getSelectedItem().toString();
        boolean isActive = checkBoxIsActive.isChecked();

        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(discountStr) || TextUtils.isEmpty(maxUsesStr) ||
                TextUtils.isEmpty(validFromStr) || TextUtils.isEmpty(validToStr)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các trường bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        Double discountValue;
        Integer maxUsesValue;
        try {
            discountValue = Double.parseDouble(discountStr);
            maxUsesValue = Integer.parseInt(maxUsesStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị giảm giá hoặc số lần sử dụng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Voucher để gửi đi
        Voucher newVoucher = new Voucher();
        newVoucher.setCode(code);
        newVoucher.setDescription(description);
        newVoucher.setDiscount(discountValue);
        newVoucher.setDiscountType(discountType.toUpperCase()); // Gửi dạng UPPERCASE nếu Enum backend là UPPERCASE
        newVoucher.setMaxUses(maxUsesValue);
        newVoucher.setValidFrom(validFromStr); // Backend sẽ parse chuỗi ISO 8601
        newVoucher.setValidTo(validToStr);
        newVoucher.setActive(isActive);
        // newVoucher.setUsedCount(0); // Backend có thể tự xử lý

        Log.d(TAG, "Attempting to add voucher: " + newVoucher.getCode());
        voucherViewModel.createVoucher(newVoucher);
    }

    private void observeViewModel() {
        voucherViewModel.getIsLoading().observe(this, isLoading -> {
            progressBarAddVoucher.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            buttonAddVoucher.setEnabled(!isLoading);
        });

        voucherViewModel.getCreateVoucherSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Toast.makeText(AddVoucherActivity.this, "Thêm voucher thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        voucherViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty() && progressBarAddVoucher.getVisibility() == View.GONE) {
                Toast.makeText(AddVoucherActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_LONG).show();
                voucherViewModel.clearErrorMessage();
            }
        });
    }
}