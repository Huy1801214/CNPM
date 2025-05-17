package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

public class SimpleDrinkCategory {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public SimpleDrinkCategory() {}

    public int getId() { return id; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}