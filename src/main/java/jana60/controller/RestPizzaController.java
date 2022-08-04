package jana60.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jana60.model.Pizza;
import jana60.repository.PizzaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/pizze")
public class RestPizzaController 
{
	
	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public List<Pizza> get()
	{
		return (List<Pizza>) repo.findAll();
	}
	
}
