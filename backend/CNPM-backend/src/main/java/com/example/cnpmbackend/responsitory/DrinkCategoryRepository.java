package com.example.cnpmbackend.responsitory;

import com.example.cnpmbackend.entity.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Integer> {
    Optional<DrinkCategory> findByNameIgnoreCase(String name);
}
