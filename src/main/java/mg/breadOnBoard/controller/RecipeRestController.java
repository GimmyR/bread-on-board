package mg.breadOnBoard.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
@AllArgsConstructor
public class RecipeRestController {
	
	private RecipeService recipeService;
	private RecipeStepService recipeStepService;
	private ImageService imageService;
	private AccountService accountService;
	
	@GetMapping("/api/recipes")
	public ResponseEntity<Iterable<RecipeResponse>> getAll(@RequestParam(name = "s", required = false) String search) {
		
		Iterable<Recipe> recipes = recipeService.findAll(search);
		return ResponseEntity.status(HttpStatus.OK).body(recipeService.mapAllToGetAll(recipes));
		
	}
	
	@GetMapping("/api/recipes/{id}")
	public ResponseEntity<RecipeResponse> getOne(@PathVariable String id) {
		
		Recipe recipe = recipeService.findOneById(id);
		return ResponseEntity.status(HttpStatus.OK).body(recipeService.mapToGetOne(recipe));
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<Recipe> create(@RequestHeader("Authorization") String authorization, @Valid @RequestBody RecipeForm form) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.create(account, form);
		return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
		
	}
	
	@PostMapping("/api/recipe/edit-image/{id}")
	public ResponseEntity<String> editImage(@RequestHeader("Authorization") String authorization, @PathVariable String id, @RequestParam MultipartFile image) {
		
		Account account = accountService.getAccountByJWT(authorization);
		
		try {
			
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			recipe = imageService.upload(recipe, image);
			recipe = recipeService.save(recipe);
			return ResponseEntity.status(HttpStatus.CREATED).body(recipe.getImage());
			
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		}
		
	}
	
	@PostMapping("/api/recipe/edit/{id}")
	public ResponseEntity<Recipe> edit(@RequestHeader("Authorization") String authorization, @PathVariable String id, @Valid @RequestBody RecipeForm form) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.update(account, id, form);
		return ResponseEntity.status(HttpStatus.OK).body(recipe);
		
	}
	
	@PostMapping("/api/recipe/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		try {
				
			Account account = accountService.getAccountByJWT(authorization);
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			recipeStepService.deleteAllByRecipeId(id);
			recipeService.delete(recipe);
			imageService.delete(recipe.getImage());
			return ResponseEntity.status(HttpStatus.CREATED).body("Recipe is successfully removed");
		
		} catch (IOException e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		}
		
	}
	
	@GetMapping("/api/recipe/author/{id}")
	public ResponseEntity<Boolean> isAuthor(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
		return ResponseEntity.status(HttpStatus.OK).body((recipe != null) && (recipe.getId().equals(id)));
		
	}

}
