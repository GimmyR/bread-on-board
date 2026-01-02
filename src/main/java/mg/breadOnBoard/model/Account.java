package mg.breadOnBoard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

}
