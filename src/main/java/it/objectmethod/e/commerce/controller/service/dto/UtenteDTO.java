package it.objectmethod.e.commerce.controller.service.dto;

public class UtenteDTO {
	
	private Long idUtente;
	private String nomeUtente;
	//private List<OrdineDTO> ordiniDellUtente;

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
