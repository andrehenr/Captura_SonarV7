package br.com.rsi.capturaSonar.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class AnaliseCodigoHK implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "ID", insertable = false)
	@Id
	private int id;

	@Column(name = "URL", length = 500)
	private String url;

	@Column(name = "Painel_Gestor", length = 500)
	private String painelGestor;

	@Column(name = "Sigla")
	private String sigla;

	@Column(name = "DataCommit")
	private String dataCommit;

	@Column(name = "Data")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSonar;

	@Column(name = "Versao", length = 500)
	private String versao;

	@Column(name = "DataCaptura")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCaptura;

	@Column(name = "Nome_Projeto", length = 500)
	private String nomeProjeto;

	@Column(name = "Linhas_Codigo")
	private int linhaCodigo;

	@Column(name = "Bugs")
	private int bugs;

	@Column(name = "Code_Smells")
	private int codeSmall;

	@Column(name = "Vulnerabilidades", nullable = true)
	private int vulnerabilidades;

	@Column(name = "Issues_Severity_Muito_Alta")
	private int issuesMuitoAlta;

	@Column(name = "Issues_Severity_Alta")
	private int issuesAlta;

	@Column(name = "Issues_Severity_Media")
	private int issuesMedia;

	@Column(name = "Issues_Severity_Baixa")
	private int issuesBaixa;

	@Column(name = "Issues_Severity_Muito_Baixa")
	private int issuesMuitoBaixa;

	@Column(name = "Vulnerability_Severity_Muito_Alta", nullable = true)
	private int vulnerabilityMuitoAlta;

	@Column(name = "Vulnerability_Severity_Alta", nullable = true)
	private int vulnerabilityAlta;

	@Column(name = "Vulnerability_Severity_Media", nullable = true)
	private int vulnerabilityMedia;

	@Column(name = "Vulnerability_Severity_Baixa", nullable = true)
	private int vulnerabilityBaixa;

	@Column(name = "Vulnerability_Severity_Muito_Baixa", nullable = true)
	private int vulnerabilityMuitoBaixa;

	@Column(name = "Descricao")
	@Lob
	private String descricao;

	@Column(name = "Debito_Tecnico")
	private String debitoTecnico;

	@Column(name = "Esforco_Confiabilidade")
	private String esforcoConfiabilidade;

	@Column(name = "Esforco_Seguranca")
	private String esforcoSeguranca;

	@Column(name = "Cobertura")
	private String cobertura;

	@Column(name = "Nota_Projeto")
	private String notaProjeto;

	@Column(name = "Nota_Anterior")
	private String notaAnterior;

	@Column(name = "Resultado")
	private String resultado;

	@Column(name = "Qualidade")
	private String qualidade;

	@Column(name = "CodigoAlteracao")
	private String codigoAlterado;
	
	@Column(name = "Debito_Tecnico_Minutos")
	private String debitoTecnicoMinutos;

	// --------------------------------------------------
	
	@Override
	public String toString() {
		return "Sigla [url=" + url + ", painelGestor=" + painelGestor + ", sigla=" + sigla + ", dataCommit="
				+ dataCommit + ", dataSonar=" + dataSonar + ", versao=" + versao + ", dataCaptura=" + dataCaptura
				+ ", nomeProjeto=" + nomeProjeto + ", linhaCodigo=" + linhaCodigo + ", bugs=" + bugs + ", codeSmall="
				+ codeSmall + ", vulnerabilidades=" + vulnerabilidades + ", issuesMuitoAlta=" + issuesMuitoAlta
				+ ", issuesAlta=" + issuesAlta + ", issuesMedia=" + issuesMedia + ", issuesBaixa=" + issuesBaixa
				+ ", issuesMuitoBaixa=" + issuesMuitoBaixa + ", vulnerabilityMuitoAlta=" + vulnerabilityMuitoAlta
				+ ", vulnerabilityAlta=" + vulnerabilityAlta + ", vulnerabilityMedia=" + vulnerabilityMedia
				+ ", vulnerabilityBaixa=" + vulnerabilityBaixa + ", vulnerabilityMuitoBaixa=" + vulnerabilityMuitoBaixa
				+ ", descricao=" + descricao + ", debitoTecnico=" + debitoTecnico + ", esforcoConfiabilidade="
				+ esforcoConfiabilidade + ", esforcoSeguranca=" + esforcoSeguranca + ", cobertura=" + cobertura
				+ ", notaProjeto=" + notaProjeto + ", notaAnterior=" + notaAnterior + ", resultado=" + resultado
				+ ", qualidade=" + qualidade + "]";
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPainelGestor(String painelGestor) {
		this.painelGestor = painelGestor;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setDataCommit(String dataCommit) {
		this.dataCommit = dataCommit;
	}

	public void setDataSonar(Date dataSonar) {
		this.dataSonar = dataSonar;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public void setDataCaptura(Date dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public void setLinhaCodigo(int linhaCodigo) {
		this.linhaCodigo = linhaCodigo;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	public void setCodeSmall(int codeSmall) {
		this.codeSmall = codeSmall;
	}

	public void setVulnerabilidades(int vulnerabilidades) {
		this.vulnerabilidades = vulnerabilidades;
	}

	public void setIssuesMuitoAlta(int issuesMuitoAlta) {
		this.issuesMuitoAlta = issuesMuitoAlta;
	}

	public void setIssuesAlta(int issuesAlta) {
		this.issuesAlta = issuesAlta;
	}

	public void setIssuesMedia(int issuesMedia) {
		this.issuesMedia = issuesMedia;
	}

	public void setIssuesBaixa(int issuesBaixa) {
		this.issuesBaixa = issuesBaixa;
	}

	public void setIssuesMuitoBaixa(int issuesMuitoBaixa) {
		this.issuesMuitoBaixa = issuesMuitoBaixa;
	}

	public void setVulnerabilityMuitoAlta(int vulnerabilityMuitoAlta) {
		this.vulnerabilityMuitoAlta = vulnerabilityMuitoAlta;
	}

	public void setVulnerabilityAlta(int vulnerabilityAlta) {
		this.vulnerabilityAlta = vulnerabilityAlta;
	}

	public void setVulnerabilityMedia(int vulnerabilityMedia) {
		this.vulnerabilityMedia = vulnerabilityMedia;
	}

	public void setVulnerabilityBaixa(int vulnerabilityBaixa) {
		this.vulnerabilityBaixa = vulnerabilityBaixa;
	}

	public void setVulnerabilityMuitoBaixa(int vulnerabilityMuitoBaixa) {
		this.vulnerabilityMuitoBaixa = vulnerabilityMuitoBaixa;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDebitoTecnico(String debitoTecnico) {
		this.debitoTecnico = debitoTecnico;
	}

	public void setEsforcoConfiabilidade(String esforcoConfiabilidade) {
		this.esforcoConfiabilidade = esforcoConfiabilidade;
	}

	public void setEsforcoSeguranca(String esforcoSeguranca) {
		this.esforcoSeguranca = esforcoSeguranca;
	}

	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}

	public void setNotaProjeto(String notaProjeto) {
		this.notaProjeto = notaProjeto;
	}

	public void setNotaAnterior(String notaAnterior) {
		this.notaAnterior = notaAnterior;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public void setQualidade(String qualidade) {
		this.qualidade = qualidade;
	}

	public void setCodigoAlterado(String codigoAlterado) {
		this.codigoAlterado = codigoAlterado;
	}

	public void setDebitoTecnicoMinutos(String debitoTecnicoMinutos) {
		this.debitoTecnicoMinutos = debitoTecnicoMinutos;
	}

//	public int getId() {
//		return id;
//	}

	public String getUrl() {
		return url;
	}

	public String getPainelGestor() {
		return painelGestor;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDataCommit() {
		return dataCommit;
	}

	public Date getDataSonar() {
		return dataSonar;
	}

	public String getVersao() {
		return versao;
	}

	public Date getDataCaptura() {
		return dataCaptura;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public int getLinhaCodigo() {
		return linhaCodigo;
	}

	public int getBugs() {
		return bugs;
	}

	public int getCodeSmall() {
		return codeSmall;
	}

	public int getVulnerabilidades() {
		return vulnerabilidades;
	}

	public int getIssuesMuitoAlta() {
		return issuesMuitoAlta;
	}

	public int getIssuesAlta() {
		return issuesAlta;
	}

	public int getIssuesMedia() {
		return issuesMedia;
	}

	public int getIssuesBaixa() {
		return issuesBaixa;
	}

	public int getIssuesMuitoBaixa() {
		return issuesMuitoBaixa;
	}

	public int getVulnerabilityMuitoAlta() {
		return vulnerabilityMuitoAlta;
	}

	public int getVulnerabilityAlta() {
		return vulnerabilityAlta;
	}

	public int getVulnerabilityMedia() {
		return vulnerabilityMedia;
	}

	public int getVulnerabilityBaixa() {
		return vulnerabilityBaixa;
	}

	public int getVulnerabilityMuitoBaixa() {
		return vulnerabilityMuitoBaixa;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDebitoTecnico() {
		return debitoTecnico;
	}

	public String getEsforcoConfiabilidade() {
		return esforcoConfiabilidade;
	}

	public String getEsforcoSeguranca() {
		return esforcoSeguranca;
	}

	public String getCobertura() {
		return cobertura;
	}

	public String getNotaProjeto() {
		return notaProjeto;
	}

	public String getNotaAnterior() {
		return notaAnterior;
	}

	public String getResultado() {
		return resultado;
	}

	public String getQualidade() {
		return qualidade;
	}

	public String getCodigoAlterado() {
		return codigoAlterado;
	}

	public String getDebitoTecnicoMinutos() {
		return debitoTecnicoMinutos;
	}
	
}
