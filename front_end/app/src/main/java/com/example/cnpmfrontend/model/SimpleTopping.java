package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

public class SimpleTopping {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Double price;

    public SimpleTopping() {}

    public int getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
}