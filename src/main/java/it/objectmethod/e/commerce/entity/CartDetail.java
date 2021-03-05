package it.objectmethod.e.commerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrello_dettaglio")
public class CartDetail {
	@GeneratedValue
	@Id
	@Column(name = "id_carrello_dettaglio")
	private Long idCarrelloDettaglio;

	@Column(name = "quantita")
	private Integer quantita;

	@OneToOne
	@JoinColumn(name = "id_articolo")
	private Articolo articolo;

	public Long getIdCarrelloDettaglio() {
		return idCarrelloDettaglio;
	}

	public void setIdCarrelloDettaglio(Long idCarrelloDettaglio) {
		this.idCarrelloDettaglio = idCarrelloDettaglio;
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
