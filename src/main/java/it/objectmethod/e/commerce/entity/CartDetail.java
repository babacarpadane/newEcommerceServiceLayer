package it.objectmethod.e.commerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrello_dettaglio")
public class CartDetail {
	@GeneratedValue
	@Id
	@Column(name = "id_carrello_dettaglio")
	private Long idCarrelloDettaglio;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Cart carrello;
	
	@Column(name = "quantita")
	private Integer quantita;
	
	@OneToOne
	private Articolo articolo;

	public Long getIdCarrelloDettaglio() {
		return idCarrelloDettaglio;
	}

	public void setIdCarrelloDettaglio(Long idCarrelloDettaglio) {
		this.idCarrelloDettaglio = idCarrelloDettaglio;
	}

	public Cart getCarrello() {
		return carrello;
	}

	public void setCarrello(Cart carrello) {
		this.carrello = carrello;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

}
