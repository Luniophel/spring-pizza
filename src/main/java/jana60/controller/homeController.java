package jana60.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jana60.model.Pizza;
import jana60.repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class HomeController 
{
	
	@Autowired
	private PizzaRepository repo;
	
	@GetMapping("/")
	public String home()
	{
		
		return "home";
		
	}
	
	//___CERCA PIZZA___//
	
		@GetMapping("/cerca")
		public String search(@RequestParam(name = "query") String query, Model model) {

			  if (query != null && query.isEmpty()) {
				  query = null;
			  }

			  List<Pizza> pizze = repo.findByNomeContaining(query);
			  model.addAttribute("pizzaList", pizze);
			  return "/sections/menu/pizza/pizzaList";
		  }
	
}
