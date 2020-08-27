package pl.javastart.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.book.recipe.RecipeRepository;

@Controller
public class HomeController {

    private RecipeRepository recipeRepository;
    public HomeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("theBest", recipeRepository.findTop3ByOrderByLikesCounterDesc());
        return "index";
    }
}