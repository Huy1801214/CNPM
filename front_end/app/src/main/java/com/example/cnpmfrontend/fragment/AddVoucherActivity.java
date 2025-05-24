package com.example.cnpmfrontend.fragment;

import android.app.Activity;
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
import com.example.cnpmfrontend.model.Voucher;
import com.example.cnpmfrontend.viewmodel.VoucherViewModel;
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

    // Huy (add voucher) 5.1.4. thu thập dữ liệu, thực hiện validation cơ bản phía client, và tạo một đối tượng Voucher (Android model).
    private void attemptAddVoucher() {
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
        newVoucher.setDiscountType(discountType);
        newVoucher.setMaxUses(maxUsesValue);
        newVoucher.setValidFrom(validFromStr);
        newVoucher.setValidTo(validToStr);
        newVoucher.setActive(isActive);

        Log.d(TAG, "Attempting to add voucher: " + newVoucher.getCode());
        // 5.1.5. AddVoucherActivity gọi phương thức CreateVoucher() trong VoucherViewModel, truyền đối tượng Voucher (Android model).
        voucherViewModel.createVoucher(newVoucher);
    }

    private void setupSpinner() {
        String[] discountTypes = {"percentage", "fixed"};
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

    // 5.1.15. AddVoucherActivity (thông qua Observer trên LiveData của VoucherViewModel) nhận được kết quả.
    private void observeViewModel() {
        voucherViewModel.getIsLoading().observe(this, isLoading -> {
            progressBarAddVoucher.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            buttonAddVoucher.setEnabled(!isLoading);
        });

        // 5.1.16. Nếu thành công, AddVoucherActivity hiển thị Toast thông báo "Thêm voucher thành công!" và đóng màn hình (finish()). 
        voucherViewModel.getErrorMessage().observe(this, errorMessage -> {
            // Thêm kiểm tra isSuccess để không hiển thị lỗi nếu đã thành công
            Boolean isSuccess = voucherViewModel.getCreateVoucherSuccess().getValue();
            if (errorMessage != null && !errorMessage.isEmpty() && (isSuccess == null || !isSuccess)) {
                if (progressBarAddVoucher.getVisibility() == View.GONE) { // Chỉ hiển thị khi không loading
                    Toast.makeText(AddVoucherActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_LONG).show();
                    voucherViewModel.clearErrorMessage(); // Xóa lỗi sau khi hiển thị
                }
            }
        });

        voucherViewModel.getCreateVoucherSuccess().observe(this, isSuccess -> {
            // Chỉ xử lý khi isSuccess không null để tránh hành động không mong muốn khi LiveData mới được observe
            if (isSuccess != null) {
                if (isSuccess) {
                    Toast.makeText(AddVoucherActivity.this, "Thêm voucher thành công!", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    // Nếu isSuccess là false, và không có lỗi cụ thể nào được set trong errorMessage
                    // (ví dụ, onFailure mà không phải ConnectException)
                    // bạn có thể muốn hiển thị một thông báo lỗi chung ở đây nếu errorMessage trống.
                    // Tuy nhiên, handleApiError và onFailure trong repository đã set errorMessage rồi.
                }
            }
        });
    }
}