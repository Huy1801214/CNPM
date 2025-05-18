package com.example.cnpmfrontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.network.ApiService; // Để lấy BASE_URL (hoặc bạn có thể truyền vào)

import java.util.List;
import java.util.Locale;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkItem> drinksList;
    private Context context;
    private String imageServerBaseUrl; // Để lưu trữ base URL cho ảnh

    public DrinkAdapter(Context context, List<DrinkItem> drinksList) {
        this.context = context;
        this.drinksList = drinksList;
        // Trích xuất phần base URL cho ảnh từ ApiService.BASE_URL
        // Ví dụ: nếu BASE_URL = "http://10.0.2.2:8080/api/", imageServerBaseUrl = "http://10.0.2.2:8080"
        if (ApiService.BASE_URL.contains("/api/")) {
            this.imageServerBaseUrl = ApiService.BASE_URL.substring(0, ApiService.BASE_URL.indexOf("/api/"));
        } else {
            // Fallback hoặc xử lý khác nếu BASE_URL không như mong đợi
            this.imageServerBaseUrl = ApiService.BASE_URL.endsWith("/") ? ApiService.BASE_URL.substring(0, ApiService.BASE_URL.length() -1) : ApiService.BASE_URL;
        }
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkItem currentItem = drinksList.get(position);

        holder.textViewDrinkName.setText(currentItem.getName());
        holder.textViewDrinkDescription.setText(currentItem.getDescription());

        if (currentItem.getPrice() != null) {
            holder.textViewDrinkPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", currentItem.getPrice()));
        } else {
            holder.textViewDrinkPrice.setText("N/A");
        }

        if (currentItem.getImageUrl() != null && !currentItem.getImageUrl().isEmpty()) {
            // Đảm bảo imageUrl từ backend bắt đầu bằng "/" nếu nó là đường dẫn tương đối từ gốc server
            String imagePath = currentItem.getImageUrl();
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }
            String fullImageUrl = imageServerBaseUrl + imagePath;

            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_placeholder_image) // Tạo drawable này trong res/drawable
                    .error(R.drawable.ic_error_image)           // Tạo drawable này
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Caching ảnh
                    .into(holder.imageViewDrink);
        } else {
            // Set ảnh mặc định nếu không có imageUrl
            holder.imageViewDrink.setImageResource(R.drawable.ic_placeholder_image);
        }
    }

    @Override
    public int getItemCount() {
        return drinksList == null ? 0 : drinksList.size();
    }

    public void setDrinks(List<DrinkItem> newDrinksList) {
        this.drinksList.clear();
        if (newDrinksList != null) {
            this.drinksList.addAll(newDrinksList);
        }
        notifyDataSetChanged(); 
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDrink;
        TextView textViewDrinkName;
        TextView textViewDrinkDescription;
        TextView textViewDrinkPrice;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDrink = itemView.findViewById(R.id.imageViewDrink);
            textViewDrinkName = itemView.findViewById(R.id.textViewDrinkName);
            textViewDrinkDescription = itemView.findViewById(R.id.textViewDrinkDescription);
            textViewDrinkPrice = itemView.findViewById(R.id.textViewDrinkPrice);
        }
    }
}