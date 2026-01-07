package mg.breadOnBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.breadOnBoard.model.Account;
import java.util.List;





@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findOneByUsernameAndPassword(String username, String password);
	
	Account findOneByIdAndPassword(Long id, String password);
	
	Account findOneByUsername(String username);
	
	@Query("select a from Account a left join fetch a.recipes where a.username = :username")
	Account findOneByUsernameWithRecipes(@Param("username") String username);
	
	List<Account> findByAdmin(Boolean admin);

}
