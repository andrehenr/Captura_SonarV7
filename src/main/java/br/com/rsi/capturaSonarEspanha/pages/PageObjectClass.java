package br.com.rsi.capturaSonarEspanha.pages;

import org.openqa.selenium.By;

public class PageObjectClass {

	private By campoLogin = By.id("login");

	private By campoSenha = By.id("password");

	private By botaoLogin = By.xpath("//*[@name='commit']");

	private By labelCobertura = By.xpath("//span[@class='js-overview-main-coverage']");

	private By labelQualityGate = By.xpath("//span[contains(@class,'level')]");

	private By labelNomePainel = By.xpath("//span/a[contains(@href,'dashboard')]");

	private By labelDataExecucao = By.xpath("//*[@class='list-inline']/li[1]");

	private By labelVersao = By.xpath("//*[@class='list-inline']/li[2]");

	private By linkAbaProblemas = By.xpath("//ul[@class='navbar-tabs']/li/a[contains(.,'Problemas')]");

	private By labelBlocker = By.xpath("//*[contains(.,'Impeditivo')]/span[2]");

	private By labelCritical = By.xpath("//*[contains(.,'Crítico')]/span[2]");

	private By labelMajor = By.xpath("//*[contains(.,'Alto')]/span[2]");

	private By labelMinor = By.xpath("//*[contains(.,'Baixo')]/span[2]");

	private By labelInfo = By.xpath("//*[contains(.,'Informativo')]/span[2]");
	
	private By labelVulnerabilidadeBlocker = By.xpath("//*[contains(.,'Impeditivo')]/span[@class='facet-stat']");

	private By labelVulnerabilidadeCritical = By.xpath("//*[contains(.,'Crítico')]/span[@class='facet-stat']");

	private By labelVulnerabilidadeMajor = By.xpath("//*[contains(.,'Alto')]/span[@class='facet-stat']");

	private By labelVulnerabilidadeMinor = By.xpath("//*[contains(.,'Baixo')]/span[@class='facet-stat']");

	private By labelVulnerabilidadeInfo = By.xpath("//*[contains(.,'Informativo')]/span[@class='facet-stat']");
	
	private By qtdBugs = By.xpath("//a[contains(.,'Bug')]/span[2]");

	private By problemasPorGravidade = By
			.xpath("//*[@class='facet search-navigator-facet search-navigator-facet-half']/span[2]");

	private By qtdVulnerabilidade = By.xpath("//a[contains(.,'Vulnerabilidade')]/span[2]");

	private By linkVulnerabilidadePaginaVisao = By
			.xpath("//a[contains(@href,'resolved=false&types=VULNERABILITY')]");

	private By qtdCodeSmell = By.xpath("//a[contains(.,'Code Smell')]/span[2]");

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
	
	public By getLabelVulnerabilidadeBlocker() {
		return labelVulnerabilidadeBlocker;
	}

	public By getLabelVulnerabilidadeCritical() {
		return labelVulnerabilidadeCritical;
	}

	public By getLabelVulnerabilidadeMajor() {
		return labelVulnerabilidadeMajor;
	}

	public By getLabelVulnerabilidadeMinor() {
		return labelVulnerabilidadeMinor;
	}

	public By getLabelVulnerabilidadeInfo() {
		return labelVulnerabilidadeInfo;
	}

	public By getProblemasPorGravidade() {
		return problemasPorGravidade;
	}

	public By getLinkVulnerabilidadePaginaVisao() {
		return linkVulnerabilidadePaginaVisao;
	}

	public By getQtdBugs() {
		return qtdBugs;
	}

	public By getLabelNomePainel() {
		return labelNomePainel;
	}

	public By getCampoLogin() {
		return campoLogin;
	}

	public By getCampoSenha() {
		return campoSenha;
	}

	public By getBotaoLogin() {
		return botaoLogin;
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

	public By getQtdVulnerabilidade() {
		return qtdVulnerabilidade;
	}

	public By getQtdCodeSmell() {
		return qtdCodeSmell;
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
