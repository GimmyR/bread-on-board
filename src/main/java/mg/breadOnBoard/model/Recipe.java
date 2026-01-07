package mg.breadOnBoard.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mg.breadOnBoard.exception.FileIsEmptyException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	
	// ATTRIBUTES :
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_sequence_generator")
	@SequenceGenerator(name = "recipe_sequence_generator", sequenceName = "RECIPE_SEQ", allocationSize = 1)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Account account;
	
	private String title;
	
	private String image;
	
	private String ingredients;
	
	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RecipeStep> recipeSteps;
	
	// METHODS :
	
	public void editTitle(String title) {
		
		this.title = title;
		
	}
	
	public void editIngredients(String ingredients) {
		
		this.ingredients = ingredients;
		
	}
	
	public void editImage(MultipartFile image) throws FileIsEmptyException {
		
		if(image.isEmpty())
			throw new FileIsEmptyException();
		
		this.image = image.getOriginalFilename();
		
	}

}
