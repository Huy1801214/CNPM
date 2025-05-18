package com.example.cnpmbackend.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCategoryDTO {
    private Integer id;
    private String name;

    public SimpleCategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleCategoryDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}