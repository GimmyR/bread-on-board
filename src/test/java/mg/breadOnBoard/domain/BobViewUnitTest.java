package mg.breadOnBoard.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class BobViewUnitTest {
	
	@Test
	public void testConfigure() {
		
		Model model = new ConcurrentModel();
		String result = BobView.configure(model, "dashboard", "Dashboard");
		assertEquals("base", result);
		assertEquals("dashboard/index", model.getAttribute("view"));
		assertEquals("Dashboard", model.getAttribute("title"));
		
	}

}
