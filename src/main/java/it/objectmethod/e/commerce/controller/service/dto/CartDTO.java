package it.objectmethod.e.commerce.controller.service.dto;

public class CartDTO {

	private Long idCarrello;
	private Long idUtente;
	private String nomeUtente;
	//private UtenteDTO proprietarioCarrello;
	//private List<CartDetailDTO> listaSpesa;
	
	public Long getIdCarrello() {
		return idCarrello;
	}
	public void setIdCarrello(Long idCarrello) {
		this.idCarrello = idCarrello;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

}
