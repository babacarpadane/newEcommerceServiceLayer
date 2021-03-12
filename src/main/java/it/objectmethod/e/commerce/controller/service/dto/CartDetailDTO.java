package it.objectmethod.e.commerce.controller.service.dto;

public class CartDetailDTO {
	
	private Long idCarrelloDettaglio;
	private Integer quantita;
	private Integer idArticolo;
	private String codiceArticolo;
	private String nomeArticolo;
	//private ArticoloDTO articolo;
	
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
	public Integer getIdArticolo() {
		return idArticolo;
	}
	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
	}
	public String getCodiceArticolo() {
		return codiceArticolo;
	}
	public void setCodiceArticolo(String codiceArticolo) {
		this.codiceArticolo = codiceArticolo;
	}
	public String getNomeArticolo() {
		return nomeArticolo;
	}
	public void setNomeArticolo(String nomeArticolo) {
		this.nomeArticolo = nomeArticolo;
	}

	
}
