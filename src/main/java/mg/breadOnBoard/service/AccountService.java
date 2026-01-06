package mg.breadOnBoard.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.AccountForm;
import mg.breadOnBoard.dto.SignUpForm;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.repository.AccountRepository;

@Service
@AllArgsConstructor
@Transactional
public class AccountService {
	
	private AccountRepository accountRepository;
	private JwtEncoder jwtEncoder;
	private JwtDecoder jwtDecoder;
	private final PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private SequenceService sequenceService;
	
	public List<Account> findAll() {
		
		return accountRepository.findAll();
		
	}
	
	public Account findById(String id) {
		
		Optional<Account> opt = accountRepository.findById(id);
		
		if(opt.isEmpty())
			throw new NotFoundException("Account not found");
		
		else return opt.get();
		
	}
	
	public Account findByUsernameAndPassword(String username, String password) {
		
		Account account = accountRepository.findOneByUsernameAndPassword(username, password);
		
		if(account == null)
			throw new NoResultException();
		
		return account;
		
	}
	
	public Account findByIdAndPassword(String id, String password) {
		
		Account account = accountRepository.findOneByIdAndPassword(id, password);
		
		if(account == null)
			throw new NoResultException();
		
		return account;
		
	}
	
	public void authenticate(AccountForm form) {
		
		Authentication auth = new UsernamePasswordAuthenticationToken(form.username(), form.password());
		authenticationManager.authenticate(auth);
		
	}
	
	public Account save(SignUpForm form) {
		
		Account account = new Account(
				
			sequenceService.generateAccountID(), 
			form.username(), 
			form.mailAddress(), 
			passwordEncoder.encode(form.password()),
			false,
			null
			
		); return accountRepository.save(account);
		
	}
	
	public void delete(Account account) {
		
		accountRepository.delete(account);
		
	}
	
	public String generateJWT(String username) {
		
		JwtClaimsSet payload = JwtClaimsSet.builder()
				.issuedAt(Instant.now())
				.subject(username)
				.build();
		
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
				JwsHeader.with(MacAlgorithm.HS512).build(), 
				payload
		);
		
		return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
		
	}
	
	public Account getAccountByJWT(String authorization) {
		
		String username = getUsernameByJWT(authorization);
		Account account = accountRepository.findOneByUsername(username);
		
		return account;
		
	}
	
	private String getUsernameByJWT(String authorization) {
		
		String jwt = authorization.substring(7);
		Jwt token = jwtDecoder.decode(jwt);
		
		return token.getSubject();
		
	}

}
