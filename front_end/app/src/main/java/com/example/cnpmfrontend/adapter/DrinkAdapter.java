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
import com.example.cnpmfrontend.R;
import com.example.cnpmfrontend.model.DrinkItem;
import com.example.cnpmfrontend.network.ApiService; // Để lấy BASE_URL
import java.util.List;
import java.util.Locale;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkItem> drinksList;
    private Context context; // Cần context để dùng Glide

    public DrinkAdapter(Context context, List<DrinkItem> drinksList) {
        this.context = context;
        this.drinksList = drinksList;
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
            holder.textViewDrinkPrice.setText(String.format(Locale.getDefault(), "%.0f VND", currentItem.getPrice()));
        } else {
            holder.textViewDrinkPrice.setText("N/A");
        }


        String serverBaseUrl = ApiService.BASE_URL.substring(0, ApiService.BASE_URL.indexOf("/api/"));
        String fullImageUrl = serverBaseUrl + currentItem.getImageUrl();

        Glide.with(context) // Hoặc holder.itemView.getContext()
                .load(fullImageUrl)
                .placeholder(R.drawable.ic_placeholder_image) // Tạo drawable này
                .error(R.drawable.ic_error_image)         // Tạo drawable này
                .into(holder.imageViewDrink);
    }

    @Override
    public int getItemCount() {
        return drinksList == null ? 0 : drinksList.size();
    }

    public void setDrinks(List<DrinkItem> drinks) {
        this.drinksList = drinks;
        notifyDataSetChanged(); // Tốt hơn nên dùng DiffUtil cho hiệu suất
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
