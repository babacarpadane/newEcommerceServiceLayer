package it.objectmethod.e.commerce.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ordine")
public class Ordine {
	@GeneratedValue
	@Id
	@Column(name = "id_ordine")
	private Integer idOrdine;

	@Column(name = "numero_ordine")
	private String numeroOrdine;

	@Column(name = "data_ordine")
	private Date dataOrdine;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_utente")
	private Utente proprietarioOrdine;

	@JsonIgnore
	@JoinColumn(name = "id_ordine")
	@OneToMany(cascade = CascadeType.ALL)
	private List<RigaOrdine> righeOrdine;

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

	public Utente getProprietarioOrdine() {
		return proprietarioOrdine;
	}

	public void setProprietarioOrdine(Utente proprietarioOrdine) {
		this.proprietarioOrdine = proprietarioOrdine;
	}

	public List<RigaOrdine> getRigheOrdine() {
		return righeOrdine;
	}

	public void setRigheOrdine(List<RigaOrdine> righeOrdine) {
		this.righeOrdine = righeOrdine;
	}

}
