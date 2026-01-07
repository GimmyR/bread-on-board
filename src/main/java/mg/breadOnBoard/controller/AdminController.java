package mg.breadOnBoard.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.domain.BobView;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;

@Controller
@AllArgsConstructor
public class AdminController {
	
	private RecipeService recipeService;
	private RecipeStepService recipeStepService;
	private ImageService imageService;
	
	@GetMapping("/sign-in")
	public String signInAsAdmin(Model model) {
		
		return BobView.configure(model, "sign-in", "Login");
		
	}
	
	@GetMapping("/")
	public String getHome(Model model, @RequestParam(name = "s", required = false) String search) {
		
		Iterable<Recipe> recipes = recipeService.findAll(search);
		model.addAttribute("recipes", recipes);
		return BobView.configure(model, "home", "Home");
		
	}
	
	@PostMapping("/recipe/remove/{id}")
	public String removeRecipe(Model model, @PathVariable Long id) throws IOException, NotFoundException {
			
		Recipe recipe = recipeService.findOneById(id, false, false);
		recipeStepService.deleteAllByRecipeId(recipe.getId());
		recipeService.deleteByRecipeId(recipe.getId());
		//recipeStepService.deleteAllByRecipe(recipe);
		//recipeService.delete(recipe);
		imageService.delete(recipe.getImage());
		return "redirect:/";
		
	}

}
