package pl.javastart.ksiazka.przepis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.javastart.ksiazka.category.CategoryRepository;

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
        if("".equals(recipe.getName()) || "".equals(recipe.getDescription()) || 0 == recipe.getPortions() || 0 == recipe.getPreparationTime()){
            return "error";
        } else {
            recipeRepository.save(recipe);
            return "addRecipeSuccess";
        }
    }

    @RequestMapping ("/likeIt")
    public String likeIt(Long id, Model model, Recipe recipe) {
        Recipe recipeById = recipeRepository.findAllById(id);
        model.addAttribute("recipe", recipeById);
        int likeIt = recipeRepository.likeIt(recipe.getId(), recipe.getLikesCounter());
        recipe.setLikesCounter(likeIt);
        model.addAttribute("like", recipeRepository.likeIt(id, recipeById.getLikesCounter()));
        return "recipe";
    }
//    @PostMapping("/likeIt")
//    public String likedIt(Recipe recipe){
//        int likeIt = recipeRepository.likeIt(recipe.getId(), recipe.getLikesCounter());
//        recipe.setLikesCounter(likeIt);
//        return "redirect:/";
//    }


//    @GetMapping("/recipe/{id}/likeIt")
//    public String likeIt(@PathVariable Long id, Model model){
//        Recipe recipeById = recipeRepository.findAllById(id);
//        int like = recipeRepository.likeIt(id, recipeById.getLikesCounter());
//        recipeById.setLikesCounter(like);
//        model.addAttribute("recipe", recipeById);
//        return "addRecipeSuccess";
//    }

    //@RequestMapping
//    @PostMapping("/recipe/{id}/likeIt")
//    public String likedIt(@PathVariable Long id, Model model, Recipe recipe){
//        int like = recipeRepository.likeIt(id);
//        recipe.setLikesCounter(like);
//        model.addAttribute("recipe", recipe);
//        System.out.println(recipe.getLikesCounter());
//        return "recipe";
//    }

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
                recipeToChange.getLikesCounter(), recipeToChange.getCategory(), recipeToChange.getPortions(), recipeToChange.getPreparationTime(), recipeToChange.getId());

        return "redirect:/recipe/" + recipeToChange.getId();
    }
}