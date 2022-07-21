package jana60.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.model.Pizza;
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
		return "/sections/menu/pizza/pizzaList";
		
	}
	
	@GetMapping("/le-nostre-pizze/nuova-pizza")
	public String pizzaForm(Model model)
	{
		
		model.addAttribute("pizza", new Pizza());
		return "/sections/menu/pizza/pizzaNew";
		
	}
	
	@PostMapping("/le-nostre-pizze/nuova-pizza")
	public String save(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult br)
	{
		
	    if (br.hasErrors())
	    	return "/le-nostre-pizze/nuova-pizza";
	    
	    else
	    {
	    	
	    	repo.save(pizzaForm);
			return "redirect:/menu/le-nostre-pizze";

	    }
		
	}

}
