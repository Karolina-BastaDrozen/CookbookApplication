package pl.javastart.ksiazka.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.javastart.ksiazka.przepis.Recipe;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipeId(Long id);
    Ingredient findAllById(Long id);

}
