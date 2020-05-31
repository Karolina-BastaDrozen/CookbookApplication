package pl.javastart.book.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipeId (Long id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Ingredient i SET i.name = :name, i.value = :value, i.unit = :unit WHERE i.id = :id")
    void update(@Param("name") String name, @Param("value") int value, @Param("unit") Unit unit, @Param("id") Long id);
}