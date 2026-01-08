package mg.breadOnBoard.domain;

import org.springframework.ui.Model;

public class BobView {
	
	public static String configure(Model model, String view, String title) {
		
		model.addAttribute("view", String.format("%s/index", view));
		model.addAttribute("title", title);
		return "base";
		
	}

}
