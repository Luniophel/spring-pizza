package jana60.controller.pizze;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.model.Ingrediente;
import jana60.repository.IngredienteRepository;

@Controller
@RequestMapping("/menu/le-nostre-pizze/ingredienti")
public class IngredientiController 
{

	@Autowired
	private IngredienteRepository repo;
	
	@GetMapping
	public String ingredientiList(Model model)
	{
		
		model.addAttribute("ingredientiList", repo.findAll());
		model.addAttribute("ingredienteNew", new Ingrediente());
		return "/sections/menu/pizza/pizzaIngredienti";
		
	}
	
	@PostMapping("/salva")
	public String ingredientiSalva(@Valid @ModelAttribute("ingredienteNew") Ingrediente ingredienteForm, BindingResult br, Model model)
	{
		if (br.hasErrors())
		{
			
			model.addAttribute("ingredientiList", repo.findAll());
			return "/sections/menu/pizza/pizzaIngredienti";

		}
		else
		{
			
			repo.save(ingredienteForm);
			return "redirect:/menu/le-nostre-pizze/ingredienti";
			
		}
		
	}
	
}
