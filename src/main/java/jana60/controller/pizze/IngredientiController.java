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
			
			br.addError(new FieldError("ingrediente", "nome", "Non può essere vuoto!"));
			model.addAttribute("ingredientiList", repo.findAll());
			return "/sections/menu/pizza/pizzaIngredienti";

		}
		else
		{
			
			repo.save(ingredienteForm);
			return "redirect:/menu/le-nostre-pizze/ingredienti";
			
		}
		
	}
	
	//___COMANDI RIMUOVI INGREDIENTI___//

		@GetMapping("/rimuovi/{id}")
		public String delete(@PathVariable("id") Integer ingredienteId, RedirectAttributes ra)
		{
			
			Optional<Ingrediente> result = repo.findById(ingredienteId);
			if(result.isPresent())
			{
				
				repo.deleteById(ingredienteId);
				ra.addFlashAttribute("successMessage", "L'ingrediente " + result.get().getNome() + " è stato eliminato con successo.");
				return "redirect:/menu/le-nostre-pizze/ingredienti";
			
			}
			
			else			
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con ID" + ingredienteId + "non presente.");
						
		}
		
}
