package br.com.rsi.capturaSonar.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class RelacaoProjetoSiglaGestor implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "ID", insertable = false)
	@Id
	private int id;

	@Column(name = "Painel_Gestor", length = 500)
	private String painelGestor;

	@Column(name = "Sigla")
	private String sigla;
	
	@Column(name = "Nome_Projeto", length = 500)
	private String nomeProjeto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPainelGestor() {
		return painelGestor;
	}

	public void setPainelGestor(String painelGestor) {
		this.painelGestor = painelGestor;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	@Override
	public String toString() {
		return "RelacaoProjetoSiglaGestor [id=" + id + ", painelGestor=" + painelGestor + ", sigla=" + sigla
				+ ", nomeProjeto=" + nomeProjeto + "]";
	}
}
