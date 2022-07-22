package jana60.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//___COMANDI NUOVA PIZZA___//
	@GetMapping("/le-nostre-pizze/nuova-pizza")
	public String pizzaForm(Model model)
	{
		
		model.addAttribute("pizza", new Pizza());
		return "/sections/menu/pizza/pizzaNew";
		
	}
	
	@PostMapping("/le-nostre-pizze/nuova-pizza")
	public String save(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult br)
	{
		boolean hasErrors = br.hasErrors();
		if(repo.countByNome(pizzaForm.getNome()) > 0)
		{
			
			br.addError(new FieldError("pizza", "nome", "Hai già una pizza con questo nome"));
			hasErrors = true;
			
		}
	    if (hasErrors)
	    	return "/sections/menu/pizza/pizzaNew";
	    
	    else
	    {
	    	
	    	repo.save(pizzaForm);
			return "redirect:/menu/le-nostre-pizze";

	    }
		
	}
	
	
	//___COMANDI RIMUOVI PIZZA___//

	@GetMapping("/le-nostre-pizze/rimuovi-pizza/{pizzaNome}/{pizzaId}")
	public String delete(@PathVariable("pizzaId") Integer pizzaId, RedirectAttributes ra)
	{
		
		Optional<Pizza> result = repo.findById(pizzaId);
		if(result.isPresent())
		{
			
			repo.deleteById(pizzaId);
			ra.addFlashAttribute("successMessage", "La " + result.get().getNome() + "è stata eliminata con successo.");
			return "redirect:/menu/le-nostre-pizze";

		}
		
		else			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con ID" + pizzaId + "non presente.");
					
	}

}
