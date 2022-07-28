package jana60.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jana60.model.Immagine;
import jana60.model.ImmagineForm;
import jana60.model.Pizza;
import jana60.repository.ImmagineRepository;
import jana60.repository.PizzaRepository;

@Service
public class ImmagineService 
{
	@Autowired
	private ImmagineRepository repo;
	
	@Autowired
	private PizzaRepository pizzaRepo;
	
	//Creiamo lista di immagini per pizza con ID specifico
	public List<Immagine> getImmaginiByPizzaId(Integer pizzaId)
	{
		
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		return repo.findByPizza(pizza);
		
	}
	
	public ImmagineForm createImmagineForm(Integer pizzaId)
	{
		
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		ImmagineForm img = new ImmagineForm();
		img.setPizza(pizza);
		return img;
		
	}
	
	//Serializzo l'immagine
	public Immagine createImmagine(ImmagineForm immagineForm) throws IOException
	{
		
		Immagine imgToSave = new Immagine();
		imgToSave.setPizza(immagineForm.getPizza());
		
		if (immagineForm.getContentMultipart() != null)
		{
			byte[] contentSerialized = immagineForm.getContentMultipart().getBytes();
			imgToSave.setContent(contentSerialized);
		}
		//Salvo immagine serializzata su db
		return repo.save(imgToSave);		
		
	}
	
	public byte[] getImmagineContent(Integer id)
	{
		
		Immagine img = repo.findById(id).get();
		return img.getContent();
		
	}
	
}
