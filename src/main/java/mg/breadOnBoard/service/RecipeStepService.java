package mg.breadOnBoard.service;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.repository.RecipeStepRepository;

@Service
@AllArgsConstructor
@Transactional
public class RecipeStepService {
	
	private RecipeStepRepository recipeStepRepository;
	private SequenceService sequenceService;
	
	public Iterable<RecipeStep> findAllByRecipeId(String recipeId) {
		
		return recipeStepRepository.findAllByRecipeId(recipeId, Sort.by(Sort.Direction.ASC, "order"));
		
	}
	
	public RecipeStep findOneById(String id) {
		
		Optional<RecipeStep> opt = recipeStepRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new NotFoundException("Recipe steps not fund");
		
	}
	
	public RecipeStep save(RecipeStep step) {
		
		if(step.getId() == null) {
			
			String id = sequenceService.generateRecipeStepID();
			step.editId(id);
			
		} return recipeStepRepository.save(step);
		
	}
	
	public void saveAll(String recipeId, Iterable<RecipeStepForm> steps) {
		
		this.deleteAllByRecipeId(recipeId);
		
		int order = 1;
		
		for(RecipeStepForm step : steps) {
			
			RecipeStep recipeStep = new RecipeStep(null, recipeId, order, step.text());
			this.save(recipeStep);
			order++;
			
		}
		
	}
	
	public void delete(RecipeStep step) {
		
		recipeStepRepository.delete(step);
		
	}
	
	public void deleteAllByRecipeId(String recipeId) {
		
		recipeStepRepository.deleteAllByRecipeId(recipeId);
		
	}

}
