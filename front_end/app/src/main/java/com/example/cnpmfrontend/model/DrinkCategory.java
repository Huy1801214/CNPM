package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;
public class DrinkCategory {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public DrinkCategory() {}

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}