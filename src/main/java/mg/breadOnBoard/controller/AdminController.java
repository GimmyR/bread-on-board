package mg.breadOnBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.breadOnBoard.domain.BobView;
import mg.breadOnBoard.dto.AccountForm;

@Controller
public class AdminController {
	
	@GetMapping("/back")
	public String signInAsAdmin(Model model) {
		
		AccountForm form = new AccountForm(null, null);
		model.addAttribute("form", form);
		return BobView.configure(model, "back/sign-in", "Sign in as Administrator");
		
	}

}
