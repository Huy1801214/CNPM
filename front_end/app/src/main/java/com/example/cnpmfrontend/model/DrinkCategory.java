package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrinkCategory {

    @SerializedName("id") // Khớp với tên field trong JSON (nếu cần)
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    // Nếu API của bạn trả về danh sách các DrinkItem thuộc về category này
    // khi bạn gọi API lấy danh sách category, thì bỏ comment dòng dưới.
    // Thường thì chúng ta sẽ lấy riêng DrinkItem theo categoryId.
     @SerializedName("drinkItems")
     private List<DrinkItem> drinkItems;

    // Constructor (Gson không bắt buộc phải có, nhưng có thể hữu ích)
    public DrinkCategory() {
    }

    public DrinkCategory(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

     public List<DrinkItem> getDrinkItems() {
         return drinkItems;
     }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     public void setDrinkItems(List<DrinkItem> drinkItems) {
        this.drinkItems = drinkItems;
     }
}
