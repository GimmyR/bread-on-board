package mg.breadOnBoard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpForm(
		@NotBlank(message = "Mail address is missing")
		@Email(message = "Mail address is invalid")
		String mailAddress,
		
		@NotBlank(message = "Username is missing")
		String username,
		
		@NotBlank(message = "Password is missing")
		String password
) {}
