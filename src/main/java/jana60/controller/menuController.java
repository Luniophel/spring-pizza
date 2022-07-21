package jana60.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.repository.PizzaRepository;

@Controller
@RequestMapping("/menu")
public class MenuController 
{
	
	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public String menu()
	{
		
		//Redirect temporaneo, manca Menu.html, previsto per dopo
		return "redirect:/menu/le-nostre-pizze";
		
	}
	
	@GetMapping("/le-nostre-pizze")
	public String pizzaList(Model model)
	{
		
		model.addAttribute("pizzaList", repo.findAll());
		return "/sections/menu/pizzaList";
		
	}
}
