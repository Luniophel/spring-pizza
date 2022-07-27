package jana60.controller.pizze;

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
@RequestMapping("/menu/le-nostre-pizze")
public class PizzeController 
{

	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public String pizzaList(Model model)
	{
		
		model.addAttribute("pizzaList", repo.findAll());
		return "/sections/menu/pizza/pizzaList";
		
	}
	
	//___COMANDI NUOVA PIZZA___//
	@GetMapping("/nuova-pizza")
	public String pizzaForm(Model model)
	{
		
		model.addAttribute("pizza", new Pizza());
		return "/sections/menu/pizza/pizzaNew";
		
	}
	
	@PostMapping("/salva-pizza")
	public String save(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult br, Model model)
	{
		boolean hasErrors = br.hasErrors();
		boolean validNome = true;
		
		//Validazione tipo di attività (UPDATE o CREATE)
		if(pizzaForm.getId() != null)	//Se possiede già un ID, allora è già stato creato, quindi siamo in UPDATE
		{
			Pizza pizzaOld = repo.findById(pizzaForm.getId()).get();
			if(pizzaOld.getNome().equalsIgnoreCase(pizzaForm.getNome()))
				validNome = false;
		}
		//Validazione unicità del Parametro NOME
		if(validNome && repo.countByNome(pizzaForm.getNome()) > 0)
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

	@GetMapping("/rimuovi-pizza/{pizzaNome}/{pizzaId}")
	public String delete(@PathVariable("pizzaId") Integer pizzaId, RedirectAttributes ra)
	{
		
		Optional<Pizza> result = repo.findById(pizzaId);
		if(result.isPresent())
		{
			
			repo.deleteById(pizzaId);
			ra.addFlashAttribute("successMessage", "La " + result.get().getNome() + " è stata eliminata con successo.");
			return "redirect:/menu/le-nostre-pizze";
		
		}
		
		else			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con ID" + pizzaId + "non presente.");
					
	}
	
	//___COMANDI MODIFICA PIZZA___//
	
	@GetMapping("/modifica-pizza/{pizzaNome}/{pizzaId}")
	public String edit(@PathVariable("pizzaId") Integer pizzaId, Model model)
	{
		
		Optional<Pizza> result = repo.findById(pizzaId);
		if(result.isPresent())
		{
			
			model.addAttribute("pizza", result.get());
			return "/sections/menu/pizza/pizzaNew";

		}
		
		else			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con ID" + pizzaId + "non presente.");
			
	}

	
}
