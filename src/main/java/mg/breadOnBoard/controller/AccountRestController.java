package mg.breadOnBoard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountRestController {
	
	private AuthenticationManager authenticationManager;
	private AccountService accountService;
	
	@PostMapping("/api/account/log-in")
	public ResponseEntity<String> logIn(@RequestParam String username, @RequestParam String password) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		String token = accountService.generateJWT(username);
		return ResponseEntity.status(HttpStatus.OK).body(token);
		
	}
	
	@GetMapping("/api/account/username")
	public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authorization) {
		
		Account account = accountService.getAccountByJWT(authorization);
		return ResponseEntity.status(HttpStatus.OK).body(account.getUsername());
		
	}

}
