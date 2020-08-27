package pl.javastart.book.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.book.recipe.RecipeRepository;

import java.util.List;

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
        if ("".equals(ingredient.getName()) || 0 >= ingredient.getValue()) {
            return "error";
        } else {
            ingredientRepository.save(ingredient);
            return "addIngredientSuccess";
        }
    }

    @GetMapping("/editIngredient")
    public String editIngredient(Model model, @RequestParam Long id){
        List<Ingredient> allByRecipeId = ingredientRepository.findByRecipeId(id);
        model.addAttribute("ingredients", allByRecipeId);
        return "editIngredients";
    }

    @PostMapping("/editIngredient")
    public String editedIngredient(List <Ingredient> ingredientsFromForm, Long recipeId) {
        List<Ingredient> allByRecipeId = ingredientRepository.findByRecipeId(recipeId);

        for (int i = 0; i < allByRecipeId.size(); i++) {
            Ingredient ingredientFromForm = ingredientsFromForm.get(i);
            Ingredient ingredientToChange = allByRecipeId.get(i);
            ingredientToChange.setName(ingredientFromForm.getName());
            ingredientToChange.setValue(ingredientFromForm.getValue());
            ingredientToChange.setUnit(ingredientFromForm.getUnit());

            ingredientRepository.update(ingredientToChange.getName(), ingredientToChange.getValue(),
                    ingredientToChange.getUnit(), ingredientToChange.getId());
        }
        return "addIngredientSuccess";
    }
}