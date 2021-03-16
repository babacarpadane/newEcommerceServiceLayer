package it.objectmethod.e.commerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {
	@GeneratedValue
	@Id
	@Column(name = "id_utente")
	private Long idUtente;

	@Column(name = "nome_utente")
	private String nomeUtente;

	@Column(name = "password")
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
