package br.com.rsi.capturaSonarEspanha.pages;

import org.openqa.selenium.By;


public class PageObjectClass {

	private By campoLogin = By.id("login");

	private By campoSenha = By.id("password");

	private By botaoLogin = By.xpath("//*[@name='commit']");
	
	private By campoPesquisaPainel = By.xpath("//*[@placeholder='Pesquise pelo nome ou pela chave']");

	private By botaoProjetos = By.xpath("//li/a[@href='/projects']");
	
	private By labelCobertura = By.xpath("//span[@class='js-overview-main-coverage']");
	
	private By labelQualityGate = By.xpath("//span[contains(@class,'level')]");
	
	private By labelDataExecucao = By.xpath("//*[@class='list-inline']/li[1]");
	
	private By labelVersao = By.xpath("//*[@class='list-inline']/li[2]");
	
	private By linkAbaProblemas = By.xpath("//ul[@class='navbar-tabs']/li/a[contains(.,'Problemas')]");
	
	private By labelBlocker = By.xpath("//a[contains(.,'Impeditivo')]/span[2]");
	
	private By labelCritical = By.xpath("//a[contains(.,'Crítico')]/span[2]");
	
	private By labelMajor = By.xpath("//a[contains(.,'Alto')]/span[2]");
	
	private By labelMinor = By.xpath("//a[contains(.,'Baixo')]/span[2]");
	
	private By labelInfo = By.xpath("//a[contains(.,'Informativo')]/span[2]");
	
	private By linkBug = By.xpath("//a[contains(.,'Bug')]");
	
	private By linkVulnerabilidade = By.xpath("//a[contains(.,'Vulnerabilidade')]");
	
	private By linkCodeSmell = By.xpath("//a[contains(.,'Code Smell')]");
	
	private By modoExibicaoPorEsforco = By.xpath("//a[contains(.,'Esforço')]");
	
	private By opcaoSolucao = By.xpath("//a[contains(.,'Solução')]");
	
	private By solucaoNaoResolvido = By.xpath("//a[contains(.,'Não resolvido')]/span[2]");
	
	private By linkAbaMedidas = By.xpath("//li/a[contains(.,'Medidas')]");
	
	private By linkConfiablidade = By.xpath("//a[contains(.,'Confiabilidade')]");
	
	private By remediationEffortConfiabilidade = By.id("measure-reliability_remediation_effort-value");
	
	private By linkSeguranca = By.xpath("//a[contains(.,'Segurança')]");
	
	private By remediationEffortSegurança = By.id("measure-security_remediation_effort-value");
	
	private By linkTamanho = By.xpath("//a[contains(.,'Tamanho')]");
	
	private By qtdLinhasCodigo = By.id("measure-ncloc-value");

	public By getCampoLogin() {
		return campoLogin;
	}

	public By getCampoSenha() {
		return campoSenha;
	}

	public By getBotaoLogin() {
		return botaoLogin;
	}

	public By getCampoPesquisaPainel() {
		return campoPesquisaPainel;
	}

	public By getBotaoProjetos() {
		return botaoProjetos;
	}

	public By getLabelCobertura() {
		return labelCobertura;
	}

	public By getLabelQualityGate() {
		return labelQualityGate;
	}

	public By getLabelDataExecucao() {
		return labelDataExecucao;
	}

	public By getLabelVersao() {
		return labelVersao;
	}

	public By getLinkAbaProblemas() {
		return linkAbaProblemas;
	}

	public By getLabelBlocker() {
		return labelBlocker;
	}

	public By getLabelCritical() {
		return labelCritical;
	}

	public By getLabelMajor() {
		return labelMajor;
	}

	public By getLabelMinor() {
		return labelMinor;
	}

	public By getLabelInfo() {
		return labelInfo;
	}

	public By getLinkBug() {
		return linkBug;
	}

	public By getLinkVulnerabilidade() {
		return linkVulnerabilidade;
	}

	public By getLinkCodeSmell() {
		return linkCodeSmell;
	}

	public By getModoExibicaoPorEsforco() {
		return modoExibicaoPorEsforco;
	}

	public By getOpcaoSolucao() {
		return opcaoSolucao;
	}

	public By getSolucaoNaoResolvido() {
		return solucaoNaoResolvido;
	}

	public By getLinkAbaMedidas() {
		return linkAbaMedidas;
	}

	public By getLinkConfiablidade() {
		return linkConfiablidade;
	}

	public By getRemediationEffortConfiabilidade() {
		return remediationEffortConfiabilidade;
	}

	public By getLinkSeguranca() {
		return linkSeguranca;
	}

	public By getRemediationEffortSegurança() {
		return remediationEffortSegurança;
	}

	public By getLinkTamanho() {
		return linkTamanho;
	}

	public By getQtdLinhasCodigo() {
		return qtdLinhasCodigo;
	}
}
