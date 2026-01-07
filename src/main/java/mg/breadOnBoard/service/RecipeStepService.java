package mg.breadOnBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.dto.RecipeStepResponse;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.repository.RecipeStepRepository;

@Service
@AllArgsConstructor
@Transactional
public class RecipeStepService {
	
	private RecipeStepRepository recipeStepRepository;
	
	public Iterable<RecipeStep> findAllByRecipeId(Long recipeId) {
		
		return recipeStepRepository.findAllByRecipeId(recipeId, Sort.by(Sort.Direction.ASC, "order"));
		
	}
	
	public RecipeStep findOneById(Long id) throws NotFoundException {
		
		Optional<RecipeStep> opt = recipeStepRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new NotFoundException("Recipe steps not fund");
		
	}
	
	public RecipeStep save(RecipeStep step) {
		
		return recipeStepRepository.save(step);
		
	}
	
	public void saveAll(Recipe recipe, Iterable<RecipeStepForm> steps) {
		
		this.deleteAllByRecipe(recipe);
		
		int order = 1;
		
		for(RecipeStepForm step : steps) {
			
			RecipeStep recipeStep = new RecipeStep(null, recipe, order, step.text());
			this.save(recipeStep);
			order++;
			
		}
		
	}
	
	public void delete(RecipeStep step) {
		
		recipeStepRepository.delete(step);
		
	}
	
	public void deleteAllByRecipe(Recipe recipe) {
		
		recipeStepRepository.deleteAllByRecipeId(recipe.getId());
		
	}
	
	public Iterable<RecipeStepResponse> mapAllToGetAllByRecipeId(Iterable<RecipeStep> steps) {
		
		List<RecipeStepResponse> result = new ArrayList<RecipeStepResponse>();
		
		steps.forEach(step -> {
			
			result.add(new RecipeStepResponse(step.getId(), step.getText()));
			
		}); return result;
		
	}

}
