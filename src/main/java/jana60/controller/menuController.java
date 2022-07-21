package jana60.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class menuController 
{
	@GetMapping
	public String menu()
	{
		
		//Redirect temporaneo, manca Menu.html, previsto per dopo
		return "redirect:/menu/le-nostre-pizze";
		
	}
	
	@GetMapping("/le-nostre-pizze")
	public String pizzaList()
	{
		
		return "/sections/menu/pizzaList";
		
	}
}
