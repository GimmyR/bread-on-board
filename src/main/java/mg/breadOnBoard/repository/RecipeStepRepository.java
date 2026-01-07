package mg.breadOnBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.RecipeStep;
import java.util.List;


@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
	
	List<RecipeStep> findAllByRecipeId(Long recipeId, Sort sort);
	
	void deleteAllByRecipeId(Long recipeId);

}
