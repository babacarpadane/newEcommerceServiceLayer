package it.objectmethod.e.commerce.controller.service.dto;

public class RigaOrdineDTO {
	
	private Long idRigaOrdine;
	private Integer idArticolo;
	private String codiceArticolo;
	private String nomeArticolo;
	//private ArticoloDTO articolo;
	private Integer quantita;
	
	public Long getIdRigaOrdine() {
		return idRigaOrdine;
	}
	public void setIdRigaOrdine(Long idRigaOrdine) {
		this.idRigaOrdine = idRigaOrdine;
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
	public Integer getQuantita() {
		return quantita;
	}
	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}
	
	
}
