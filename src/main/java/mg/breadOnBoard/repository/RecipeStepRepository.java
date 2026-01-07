package mg.breadOnBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.RecipeStep;
import java.util.List;


@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
	
	@Query("select rs from RecipeStep rs join fetch rs.recipe r where r.id = :recipe_id order by rs.order asc")
	List<RecipeStep> findAllByRecipeIdOrderByOrderASC(@Param("recipe_id") Long recipeId);

}