package mg.breadOnBoard.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mg.breadOnBoard.exception.FileIsEmptyException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	
	public static final String PREFIX = "R";
	
	public static final int LENGTH_ID = 5;
	
	// ATTRIBUTES :
	
	@Id
	private String id;
	
	@Column(name = "account_id")
	private String accountId;
	
	private String title;
	
	private String image;
	
	private String ingredients;
	
	// METHODS :
	
	public void editTitle(String title) {
		
		this.title = title;
		
	}
	
	public void editIngredients(String ingredients) {
		
		this.ingredients = ingredients;
		
	}
	
	public void editImage(MultipartFile image) {
		
		if(image.isEmpty())
			throw new FileIsEmptyException();
		
		this.image = image.getOriginalFilename();
		
	}

}
