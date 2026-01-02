package mg.breadOnBoard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.StepsForm;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
@AllArgsConstructor
public class RecipeStepRestController {
	
	private RecipeStepService recipeStepService;
	private AccountService accountService;
	private RecipeService recipeService;
	
	@GetMapping("/api/recipe-steps/{recipeId}")
	public Iterable<RecipeStep> getAllByRecipeId(@PathVariable String recipeId) {
		
		return recipeStepService.findAllByRecipeId(recipeId);
		
	}
	
	@PostMapping("/api/recipe-steps/save")
	public ResponseEntity<String> saveAll(@RequestHeader("Authorization") String authorization, @Valid @RequestBody StepsForm form) {
			
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.findByIdAndAccountId(form.recipeId(), account.getId());
		recipeStepService.saveAll(recipe.getId(), form.steps());
		return ResponseEntity.status(HttpStatus.OK).body(recipe.getId());
		
	}

}
