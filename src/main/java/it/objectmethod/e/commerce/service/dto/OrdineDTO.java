package it.objectmethod.e.commerce.service.dto;

import java.util.Date;

public class OrdineDTO {
	
	private Integer idOrdine;
	private String numeroOrdine;
	private Date dataOrdine;
	private Long idUtente;
	private String nomeUtente;
	//private List<RigaOrdineDTO> righeOrdine;
	public Integer getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(Integer idOrdine) {
		this.idOrdine = idOrdine;
	}
	public String getNumeroOrdine() {
		return numeroOrdine;
	}
	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}
	public Date getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
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
