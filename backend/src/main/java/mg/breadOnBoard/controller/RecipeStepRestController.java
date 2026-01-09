package mg.breadOnBoard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.dto.RecipeStepResponse;
import mg.breadOnBoard.dto.StepsForm;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
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
	public ResponseEntity<Iterable<RecipeStepResponse>> getAllByRecipeId(@PathVariable Long recipeId) throws NotFoundException {
		
		Recipe recipe = recipeService.findOneById(recipeId, false, true);
		Iterable<RecipeStepResponse> res = recipeStepService.mapAllToGetAllByRecipeId(recipe.getRecipeSteps());
		return ResponseEntity.status(HttpStatus.OK).body(res);
		
	}
	
	@PostMapping("/api/recipe-steps/save")
	public ResponseEntity<RecipeResponse> saveAll(Authentication auth, @Valid @RequestBody StepsForm form) throws NotFoundException {
			
		Account account = accountService.getAccountByAuthentication(auth, false);
		Recipe recipe = recipeService.findByIdAndAccountId(form.recipeId(), account.getId(), true, true);
		recipeStepService.saveAll(recipe, form.steps());
		RecipeResponse res = recipeService.mapToGetOne(recipe);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
		
	}

}
