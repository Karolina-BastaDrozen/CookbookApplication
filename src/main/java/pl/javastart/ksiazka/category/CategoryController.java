package pl.javastart.ksiazka.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/{id}")
       public String category(@PathVariable Long id, Model model){
        Optional<Category> byId = categoryRepository.findById(id);

        if(byId.isPresent()){
            Category category = byId.get();
            model.addAttribute("category", category);
            return "category";
        } else {
            return "error";
        }
    }
}