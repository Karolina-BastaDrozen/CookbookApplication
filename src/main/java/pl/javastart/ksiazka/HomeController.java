package pl.javastart.ksiazka;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.ksiazka.przepis.RecipeRepository;

@Controller
public class HomeController {


    private RecipeRepository recipeRepository;

    public HomeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("theBest", recipeRepository.findTop3ByOrderByLikesCounterDesc());
        return "home";
    }
}