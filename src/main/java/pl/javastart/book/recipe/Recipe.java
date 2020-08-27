package pl.javastart.book.recipe;

import pl.javastart.book.ingredient.Ingredient;
import pl.javastart.book.category.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private int preparationTime;

    @Column(nullable = false)
    private int portions;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    private int likesCounter;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    private Category category;

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Przepis nr %d, nazwa: %s, opis: %s, czas przygotowania: %d, ilość porcji: %d, " +
                "poziom trudności %s, polubienia: %d, kategoria: %s, składniki: %s", id, name, description,
                preparationTime, portions, difficultyLevel, likesCounter, ingredients, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return preparationTime == recipe.preparationTime &&
                portions == recipe.portions &&
                likesCounter == recipe.likesCounter &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(description, recipe.description) &&
                difficultyLevel == recipe.difficultyLevel &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(category, recipe.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, preparationTime, portions, difficultyLevel, likesCounter, ingredients, category);
    }

    public void likeIt() {
        this.likesCounter++;
    }

    public void dislikeIt() {
        this.likesCounter--;
    }
}