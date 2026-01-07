package mg.breadOnBoard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.AccountForm;
import mg.breadOnBoard.dto.SignUpForm;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountRestController {
	
	private AccountService accountService;
	
	@PostMapping("/api/sign-in")
	public ResponseEntity<String> logIn(@Valid @RequestBody AccountForm account) {
		
		accountService.authenticate(account);
		String token = accountService.generateJWT(account.username());
		return ResponseEntity.status(HttpStatus.OK).body(token);
		
	}
	
	@GetMapping("/api/username")
	public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authorization) {
		
		Account account = accountService.getAccountByJWT(authorization, false);
		return ResponseEntity.status(HttpStatus.OK).body(account.getUsername());
		
	}
	
	@PostMapping("/api/sign-up")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignUpForm form) {
		
		Account account = accountService.save(form);
		String token = accountService.generateJWT(account.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(token);
		
	}

}
