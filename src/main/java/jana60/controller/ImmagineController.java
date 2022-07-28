package jana60.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jana60.model.Immagine;
import jana60.model.ImmagineForm;
import jana60.service.ImmagineService;

@Controller
@RequestMapping("/immagine")
public class ImmagineController 
{

	@Autowired
	private ImmagineService service;
	
	@GetMapping("/{pizzaId}")
	public String pizzaImmagini(@PathVariable("pizzaId") Integer pizzaId, Model model)
	{
		
		List<Immagine> immagini = service.getImmaginiByPizzaId(pizzaId);
		ImmagineForm immagineForm = service.createImmagineForm(pizzaId);
		
		model.addAttribute("immagineList", immagini);
		model.addAttribute("immagineForm", immagineForm);
		return "/images/list";
		
	}
	
	 @PostMapping("/salva")
	  public String saveImage(@ModelAttribute("immagineForm") ImmagineForm immagineForm) {
	    // devo salvare l'immagine su database
	    try {
	      Immagine savedImage = service.createImmagine(immagineForm);
	      return "redirect:/immagine/" + savedImage.getPizza().getId();
	    } catch (IOException e) {
	      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save image");
	    }
	  }

	  /*
	   * Controller che in base all'id dell'Image restituisce il contenuto
	   */
	  @RequestMapping(value = "/{immagineId}/content", produces = MediaType.IMAGE_JPEG_VALUE)
	  public ResponseEntity<byte[]> getImageContent(@PathVariable("immagineId") Integer immagineId) {
	    // recupero il content dal database
	    byte[] content = service.getImmagineContent(immagineId);
	    // preparo gli headers della response con il tipo di contenuto
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    // ritorno il contenuto, gli headers e lo status http
	    return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	  }

}
