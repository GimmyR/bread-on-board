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
import mg.breadOnBoard.exception.FileIsEmptyException;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeService;

@RestController
@AllArgsConstructor
public class RecipeRestController {
	
	private RecipeService recipeService;
	private ImageService imageService;
	private AccountService accountService;
	
	@GetMapping("/api/recipes")
	public ResponseEntity<Iterable<RecipeResponse>> getAll(@RequestParam(name = "s", required = false) String search) {
		
		Iterable<Recipe> recipes = recipeService.findAll(search);
		Iterable<RecipeResponse> res = recipeService.mapAllToGetAll(recipes);
		return ResponseEntity.status(HttpStatus.OK).body(res);
		
	}
	
	@GetMapping("/api/recipes/{id}")
	public ResponseEntity<RecipeResponse> getOne(@PathVariable Long id) throws NotFoundException {
		
		Recipe recipe = recipeService.findOneById(id, true, true);
		return ResponseEntity.status(HttpStatus.OK).body(recipeService.mapToGetOne(recipe));
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<RecipeResponse> create(@RequestHeader("Authorization") String authorization, @Valid @RequestBody RecipeForm form) {
		
		Account account = accountService.getAccountByJWT(authorization, false);
		Recipe recipe = recipeService.create(account, form);
		RecipeResponse res = recipeService.mapToGetOne(recipe);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
		
	}
	
	@PostMapping("/api/recipe/edit-image/{id}")
	public ResponseEntity<String> editImage(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestParam MultipartFile image) throws IOException, NotFoundException, FileIsEmptyException {
		
		Account account = accountService.getAccountByJWT(authorization, false);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId(), false, false);
		recipe = imageService.upload(recipe, image);
		recipe = recipeService.save(recipe);
		return ResponseEntity.status(HttpStatus.CREATED).body(recipe.getImage());
		
	}
	
	@PostMapping("/api/recipe/edit/{id}")
	public ResponseEntity<RecipeResponse> edit(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @Valid @RequestBody RecipeForm form) throws NotFoundException {
		
		Account account = accountService.getAccountByJWT(authorization, false);
		Recipe recipe = recipeService.update(account, id, form);
		RecipeResponse res = recipeService.mapToGetOne(recipe);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
		
	}
	
	@PostMapping("/api/recipe/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("Authorization") String authorization, @PathVariable Long id) throws IOException, NotFoundException {
			
		Account account = accountService.getAccountByJWT(authorization, false);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId(), false, true);
		recipe.getRecipeSteps().clear();
		recipeService.delete(recipe);
		imageService.delete(recipe.getImage());
		return ResponseEntity.status(HttpStatus.CREATED).body("Recipe is successfully removed");
		
	}
	
	@GetMapping("/api/recipe/author/{id}")
	public ResponseEntity<Boolean> isAuthor(@RequestHeader("Authorization") String authorization, @PathVariable Long id) throws NotFoundException {
		
		Account account = accountService.getAccountByJWT(authorization, false);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId(), false, false);
		return ResponseEntity.status(HttpStatus.OK).body((recipe != null) && (recipe.getId().equals(id)));
		
	}
	
	@GetMapping("/api/my-recipes")
	public ResponseEntity<Iterable<RecipeResponse>> getMyRecipes(@RequestHeader("Authorization") String authorization) {
		
		Account account = accountService.getAccountByJWT(authorization, true);
		Iterable<Recipe> recipes = account.getRecipes();
		Iterable<RecipeResponse> res = recipeService.mapAllToGetAll(recipes);
		return ResponseEntity.ok(res);
		
	}

}
