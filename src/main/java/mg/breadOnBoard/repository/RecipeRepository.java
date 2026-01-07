package mg.breadOnBoard.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Recipe;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	Iterable<Recipe> findByAccountId(Long accountId, Sort sort);
	
	Iterable<Recipe> findByTitleLike(String title, Sort sort);
	
	Recipe findOneByIdAndAccountId(Long id, Long accountId);
	
	@Query("select r from Recipe r join fetch r.account a left join fetch r.recipeSteps s where r.id = :id and a.id = :account_id")
	Recipe findOneByIdAndAccountIdWithAccountAndWithSteps(@Param("id") Long id, @Param("account_id") Long accountId);
	
	@Query("select r from Recipe r join fetch r.account left join fetch r.recipeSteps where r.id = :id")
	Optional<Recipe> findByIdWithAccountAndWithSteps(@Param("id") Long id);
	
	@Query("select r from Recipe r left join fetch r.recipeSteps where r.id = :id")
	Optional<Recipe> findByIdWithSteps(@Param("id") Long id);

}
