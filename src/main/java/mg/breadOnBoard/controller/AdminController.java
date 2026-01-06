package mg.breadOnBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.domain.BobView;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.RecipeService;

@Controller
@AllArgsConstructor
public class AdminController {
	
	private RecipeService recipeService;
	
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

}
