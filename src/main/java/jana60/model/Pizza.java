package jana60.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pizza")
public class Pizza 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "La pizza deve avere un nome")
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Lob
	private String descrizione;
	
	@NotNull(message = "Non penso tu voglia regalare le tue pizze...")
	@Min(value = 0)
	private Double prezzo;
	
	@ManyToMany
	@JoinTable
	(
			name = "pizza_ingredienti",
			joinColumns = {@JoinColumn(name = "pizza_id")},
			inverseJoinColumns = {@JoinColumn(name = "ingredienti_id")}
	)
	private List<Ingrediente> ingredientiPizza;
		
	
	//GETTER & SETTER

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public List<Ingrediente> getIngredientiPizza() {
		return ingredientiPizza;
	}

	public void setIngredientiPizza(List<Ingrediente> ingredientiPizza) {
		this.ingredientiPizza = ingredientiPizza;
	}
	
}
