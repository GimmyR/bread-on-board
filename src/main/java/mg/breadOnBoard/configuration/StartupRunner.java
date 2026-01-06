package mg.breadOnBoard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import mg.breadOnBoard.service.AccountService;

@Configuration
public class StartupRunner implements CommandLineRunner {
	
	@Autowired
	private AccountService accountService;

	@Value("${ADMIN_USERNAME}")
	private String adminUsername;
	
	@Value("${ADMIN_MAIL_ADDRESS}")
	private String adminMailAddress;
	
	@Value("${ADMIN_PASSWORD}")
	private String adminPassword;

	@Override
	public void run(String... args) throws Exception {
		
		if(!accountService.adminExists())
			accountService.saveAdmin(adminUsername, adminMailAddress, adminPassword);
		
	}

}
