package it.objectmethod.e.commerce.service.dto;

import java.util.List;

public class CartDTO {

	private Long idCarrello;
	private Long idUtente;
	private String nomeUtente;
	private List<CartDetailDTO> listaSpesa;
	//private UtenteDTO proprietarioCarrello;
	
	public List<CartDetailDTO> getListaSpesa() {
		return listaSpesa;
	}
	public void setListaSpesa(List<CartDetailDTO> listaSpesa) {
		this.listaSpesa = listaSpesa;
	}
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
