package com.example.cnpmbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;

@Entity
@Table(name = "drink_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Lob
    private String description;

    private Double price;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private DrinkCategory category;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean available = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "drink_toppings",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Set<Topping> toppings;

    public DrinkItem() {
    }

    public DrinkItem(Integer id, String name, String description, Double price, String imageUrl, DrinkCategory category, Boolean available, Set<Topping> toppings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.available = available;
        this.toppings = toppings;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DrinkCategory getCategory() {
        return category;
    }

    public void setCategory(DrinkCategory category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }
}
