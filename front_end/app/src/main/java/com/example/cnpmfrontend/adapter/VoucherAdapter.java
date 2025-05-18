package com.example.cnpmfrontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.model.Voucher;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private List<Voucher> voucherList;
    private Context context;

    public VoucherAdapter(Context context, List<Voucher> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher currentVoucher = voucherList.get(position);
        holder.textViewVoucherCode.setText(currentVoucher.getCode());
        holder.textViewVoucherDescription.setText(currentVoucher.getDescription());

        // Hiển thị thông tin giảm giá
        if ("percentage".equalsIgnoreCase(currentVoucher.getDiscountType())) {
            if (currentVoucher.getDiscount() != null) {
                holder.textViewVoucherDiscount.setText(String.format(Locale.getDefault(), "Giảm %.0f%%", currentVoucher.getDiscount() * 100));
            } else {
                holder.textViewVoucherDiscount.setText("N/A");
            }
        } else if ("fixed_amount".equalsIgnoreCase(currentVoucher.getDiscountType())) { // Giả sử có loại này
            if (currentVoucher.getDiscount() != null) {
                holder.textViewVoucherDiscount.setText(String.format(Locale.getDefault(), "Giảm %,.0f VND", currentVoucher.getDiscount()));
            } else {
                holder.textViewVoucherDiscount.setText("N/A");
            }
        } else {
            holder.textViewVoucherDiscount.setText(currentVoucher.getDiscountType()); // Hoặc một mô tả chung
        }


        // Định dạng ngày hết hạn
        if (currentVoucher.getValidTo() != null) {
            try {
                // Giả sử backend trả về dạng ISO 8601: "yyyy-MM-dd'T'HH:mm:ss" hoặc "yyyy-MM-dd"
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                // Nếu backend chỉ trả yyyy-MM-dd thì dùng:
                // SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date expiryDate = sdfInput.parse(currentVoucher.getValidTo());

                SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                holder.textViewVoucherExpiry.setText("HSD: " + sdfOutput.format(expiryDate));
            } catch (ParseException e) {
                holder.textViewVoucherExpiry.setText("HSD: " + currentVoucher.getValidTo()); // Hiển thị chuỗi gốc nếu parse lỗi
            }
        } else {
            holder.textViewVoucherExpiry.setText("HSD: Không xác định");
        }
    }

    @Override
    public int getItemCount() {
        return voucherList == null ? 0 : voucherList.size();
    }

    public void setVouchers(List<Voucher> newVouchers) {
        this.voucherList.clear();
        if (newVouchers != null) {
            this.voucherList.addAll(newVouchers);
        }
        notifyDataSetChanged();
    }

    static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVoucherCode;
        TextView textViewVoucherDescription;
        TextView textViewVoucherDiscount;
        TextView textViewVoucherExpiry;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVoucherCode = itemView.findViewById(R.id.textViewVoucherCode);
            textViewVoucherDescription = itemView.findViewById(R.id.textViewVoucherDescription);
            textViewVoucherDiscount = itemView.findViewById(R.id.textViewVoucherDiscount);
            textViewVoucherExpiry = itemView.findViewById(R.id.textViewVoucherExpiry);
        }
    }
}