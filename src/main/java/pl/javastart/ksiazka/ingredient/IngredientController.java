package pl.javastart.ksiazka.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.ksiazka.przepis.Recipe;
import pl.javastart.ksiazka.przepis.RecipeRepository;

@Controller
public class IngredientController {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/addIngredient")
    public String addIngredient(Model model) {
        Ingredient ingredient = new Ingredient();
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("recipes", recipeRepository.findAll());
        return "addIngredient";
    }

    @PostMapping("/addIngredient")
    public String addedIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return "addIngredientSuccess";
    }

    @GetMapping("/editIngredient")
    public String editIngredient(Model model, @RequestParam Long id) {
        model.addAttribute("ingredients", ingredientRepository.findAllByRecipeId(id));
     //   model.addAttribute("ingredient", )
        return "editIngredients";
    }

}