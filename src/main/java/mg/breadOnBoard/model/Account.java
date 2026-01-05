package mg.breadOnBoard.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	// ATTRIBUTES :
	
	public static final String PREFIX = "A";
	
	public static final int LENGTH_ID = 4; // Minus 1 because it's without the prefix
	
	@Id
	private String id;
	
	private String username;
	
	@Column(name = "mail_address")
	private String mailAddress;
	
	private String password;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Recipe> recipes;

}
