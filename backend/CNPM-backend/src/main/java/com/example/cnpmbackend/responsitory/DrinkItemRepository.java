package com.example.cnpmbackend.responsitory;

import com.example.cnpmbackend.entity.DrinkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrinkItemRepository extends JpaRepository<DrinkItem, Integer> {

    Optional<DrinkItem> findByNameIgnoreCase(String name);

    List<DrinkItem> findByCategory_Id(Integer categoryId);

    List<DrinkItem> findByCategory_IdAndAvailableTrue(Integer categoryId);

    List<DrinkItem> findByAvailableTrue();

    List<DrinkItem> findByPriceBetween(Double minPrice, Double maxPrice);

    Page<DrinkItem> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(
            String nameKeyword, String descriptionKeyword, Pageable pageable);

    @Query("SELECT di FROM DrinkItem di WHERE di.category.id = :categoryId AND LOWER(di.name) LIKE LOWER(concat('%', :nameKeyword, '%'))")
    List<DrinkItem> findByCategoryIdAndNameKeyword(
            @Param("categoryId") Integer categoryId,
            @Param("nameKeyword") String nameKeyword
    );

    @Query("SELECT DISTINCT di FROM DrinkItem di LEFT JOIN FETCH di.toppings WHERE di.id = :drinkItemId")
    Optional<DrinkItem> findByIdWithToppings(@Param("drinkItemId") Integer drinkItemId);
}
