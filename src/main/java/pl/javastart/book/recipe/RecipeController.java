package pl.javastart.book.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.javastart.book.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "allCategories";
    }

    @GetMapping("/recipes")
    public String recipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/recipe/{id}")
    public String recipe(@PathVariable Long id, Model model) {
        Optional<Recipe> recipeById = recipeRepository.findById(id);
        if(recipeById.isPresent()){
            Recipe recipe = recipeById.get();
            model.addAttribute("recipe", recipe);
            return "recipe";
        } else {
            return "error";
        }
    }

    @GetMapping("/recipe/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/addRecipe")
    public String addRecipe(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("mode", "add");
        return "addRecipe";
    }

    @PostMapping("/addRecipe")
    public String addRecipe(Recipe recipe) {
        if("".equals(recipe.getName()) || "".equals(recipe.getDescription()) || 0 >= recipe.getPortions() || 0 >= recipe.getPreparationTime()){
            return "error";
        } else {
            recipeRepository.save(recipe);
            return "addRecipeSuccess";
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Recipe recipe = recipeRepository.findAllById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editRecipe";
    }

    @PostMapping("/edit")
    public String edited(Recipe recipeFromForm){
        Recipe recipeToChange = recipeRepository.findAllById(recipeFromForm.getId());
        recipeToChange.setName(recipeFromForm.getName());
        recipeToChange.setDescription(recipeFromForm.getDescription());
        recipeToChange.setCategory(recipeFromForm.getCategory());
        recipeToChange.setDifficultyLevel(recipeFromForm.getDifficultyLevel());
        recipeToChange.setPortions(recipeFromForm.getPortions());
        recipeToChange.setPreparationTime(recipeFromForm.getPreparationTime());

        recipeRepository.update(recipeToChange.getName(), recipeToChange.getDescription(), recipeToChange.getDifficultyLevel(),
                recipeToChange.getLikesCounter(), recipeToChange.getCategory(), recipeToChange.getPortions(),
                recipeToChange.getPreparationTime(), recipeToChange.getId());

        return "redirect:/recipe/" + recipeToChange.getId();
    }

    @GetMapping ("/likeIt")
    public String likeIt(Model model, @RequestParam Long id) {

        Recipe recipeById = recipeRepository.findAllById(id);
        recipeById.likeIt();
        recipeRepository.save(recipeById);
        model.addAttribute("recipe", recipeById);
        return "redirect:/recipe/" + id;
    }

    @GetMapping ("/dislikeIt")
    public String disLikeIt(Model model, @RequestParam Long id) {

        Recipe recipeById = recipeRepository.findAllById(id);
        recipeById.dislikeIt();
        recipeRepository.save(recipeById);
        model.addAttribute("recipe", recipeById);
        return "redirect:/recipe/" + id;
    }
}