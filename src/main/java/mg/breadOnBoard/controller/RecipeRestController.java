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
	public ResponseEntity<Iterable<Recipe>> getAll(@RequestParam(name = "s", required = false) String search) {
		
		return ResponseEntity.status(HttpStatus.OK).body(recipeService.findAll(search));
		
	}
	
	@GetMapping("/api/recipes/{id}")
	public ResponseEntity<Recipe> getOne(@PathVariable String id) {
		
		Recipe recipe = recipeService.findOneById(id);
		return ResponseEntity.status(HttpStatus.OK).body(recipe);
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<String> create(@RequestHeader("Authorization") String authorization, @Valid @RequestBody RecipeForm form) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.create(account, form);
		return ResponseEntity.status(HttpStatus.CREATED).body(recipe.getId());
		
	}
	
	@PostMapping("/api/recipe/edit-image/{id}")
	public ResponseEntity<String> editImage(@RequestHeader("Authorization") String authorization, @PathVariable String id, @RequestParam MultipartFile image) {
		
		ResponseEntity<String> response = null;
		Account account = accountService.getAccountByJWT(authorization);
		
		try {
			
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			recipe = imageService.upload(recipe, image);
			recipeService.save(recipe);
			
		} catch (IOException e) {

			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/edit/{id}")
	public ResponseEntity<String> edit(@RequestHeader("Authorization") String authorization, @PathVariable String id, @Valid @RequestBody RecipeForm form) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.update(account, id, form);
		return ResponseEntity.status(HttpStatus.OK).body(recipe.getId());
		
	}
	
	@PostMapping("/api/recipe/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		ResponseEntity<String> response = null;
		
		try {
				
			Account account = accountService.getAccountByJWT(authorization);
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			recipeStepService.deleteAllByRecipeId(id);
			recipeService.delete(recipe);
			imageService.delete(recipe.getImage());
			response = ResponseEntity.status(HttpStatus.CREATED).body("Recipe is successfully removed");
		
		} catch (IOException e) {
			
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		} return response;
		
	}
	
	@GetMapping("/api/recipe/author/{id}")
	public ResponseEntity<Boolean> isAuthor(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
		return ResponseEntity.status(HttpStatus.OK).body((recipe != null) && (recipe.getId().equals(id)));
		
	}

}
