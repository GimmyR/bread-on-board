package mg.breadOnBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.breadOnBoard.domain.BobView;

@Controller
public class AdminController {
	
	@GetMapping("/sign-in")
	public String signInAsAdmin(Model model) {
		
		return BobView.configure(model, "sign-in", "Login");
		
	}
	
	@GetMapping("/")
	public String getHome(Model mode) {
		
		return BobView.configure(mode, "home", "Home");
		
	}

}
