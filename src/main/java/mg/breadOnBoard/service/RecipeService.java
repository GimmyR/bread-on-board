package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.repository.RecipeRepository;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService {
	
	private RecipeRepository recipeRepository;
	private SequenceService sequenceService;
	
	public Iterable<Recipe> findAll() {
		
		return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Iterable<Recipe> findAllByAccountId(String accountId) {
		
		return recipeRepository.findByAccountId(accountId, Sort.by(Sort.Direction.ASC, "accountId"));
		
	}
	
	public Iterable<Recipe> findAllByTitleOrIngredients(String search) {
		
		String toSearch = "%" + search + "%";
		
		return recipeRepository.findByTitleLikeOrIngredientsLike(toSearch, toSearch, Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Recipe findOneById(String id) throws NotFoundException {
		
		Optional<Recipe> opt = recipeRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new NotFoundException("Recipe not found");
		
	}
	
	public Recipe findByIdAndAccountId(String id, String accountId) {
		
		Recipe recipe = recipeRepository.findOneByIdAndAccountId(id, accountId);
		
		if(recipe == null)
			throw new NotFoundException("Recipe not found");
		
		return recipe;
		
	}
	
	public Recipe save(Recipe recipe) {
		
		if(recipe.getId() == null) {
			
			String id = sequenceService.generateRecipeID();
			recipe.editId(id);
			
		} return recipeRepository.save(recipe);
		
	}
	
	public void delete(Recipe recipe) {
		
		recipeRepository.delete(recipe);
		
	}

}
