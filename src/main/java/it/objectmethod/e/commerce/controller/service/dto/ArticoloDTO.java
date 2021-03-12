package it.objectmethod.e.commerce.controller.service.dto;

public class ArticoloDTO {
	
	private Integer idArticolo;
	private Integer disponibilita;
	private String codiceArticolo;
	private String nomeArticolo;
	private Double prezzoUnitario;
	
	public Integer getIdArticolo() {
		return idArticolo;
	}
	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
	}
	public Integer getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(Integer disponibilita) {
		this.disponibilita = disponibilita;
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
	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}
	public void setPrezzoUnitario(Double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}
	
}
