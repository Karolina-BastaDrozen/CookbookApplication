package pl.javastart.book.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.javastart.book.category.Category;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe findAllById(Long id);

    List<Recipe> findTop3ByOrderByLikesCounterDesc();

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Recipe r SET r.name = :name, r.description = :description, r.difficultyLevel = :difficultyLevel, r.likesCounter = :likesCounter," +
            "r.category = :category, r.portions = :portions, r.preparationTime = :preparationTime WHERE r.id = :id")
    void update(@Param("name") String name, @Param("description") String description, @Param("difficultyLevel") DifficultyLevel difficultyLevel,
                @Param("likesCounter") int likesCounter, @Param("category") Category category, @Param("portions") int portions,
                @Param("preparationTime") int preparationTime, @Param("id") Long id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Recipe r SET r.likesCounter = :likesCounter + 1 WHERE r.id = :id")
    int likeIt(@Param("id") Long id, @Param("likesCounter") int likesCounter);

    @Transactional
    @Modifying
    @Query("UPDATE Recipe r SET r.likesCounter = :likesCounter - 1 WHERE r.id = :id")
    int dislikeIt(@Param("id") Long id, @Param("likesCounter") int likesCounter);
}