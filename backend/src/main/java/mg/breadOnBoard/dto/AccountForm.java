package mg.breadOnBoard.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountForm(
		@NotBlank(message = "Invalid credentials")
		String username, 
		
		@NotBlank(message = "Invalid credentials")
		String password
) {}