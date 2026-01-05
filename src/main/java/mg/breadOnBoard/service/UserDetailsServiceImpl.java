package mg.breadOnBoard.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.repository.AccountRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = accountRepository.findOneByUsername(username);
		
		if(account == null)
			throw new UsernameNotFoundException(String.format("%s not found", username));
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		UserDetails user = User.withUsername(account.getUsername())
								.password(account.getPassword())
								.authorities(authorities).build();
		
		return user;
		
	}

}
