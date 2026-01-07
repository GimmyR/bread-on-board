package mg.breadOnBoard.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Recipe;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
	
	Iterable<Recipe> findByAccountId(String accountId, Sort sort);
	
	Iterable<Recipe> findByTitleLike(String title, Sort sort);
	
	Recipe findOneByIdAndAccountId(String id, String accountId);
	
	@Query("select r from Recipe r join fetch r.account join fetch r.recipeSteps where r.id = :id and r.account.id = :account_id")
	Recipe findOneByIdAndAccountIdWithAccountAndWithSteps(@Param("id") String id, @Param("account_id") String accountId);
	
	@Query("select r from Recipe r join fetch r.account join fetch r.recipeSteps where r.id = :id")
	Optional<Recipe> findByIdWithAccountAndWithSteps(@Param("id") String id);

}
