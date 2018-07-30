package br.com.rsi.capturaSonarEspanha.pages;

import org.openqa.selenium.By;


public class PageObjectClass {

	private By campoLogin = By.id("login");

	private By campoSenha = By.id("password");

	private By botaoLogin = By.xpath("//*[@name='commit']");

	private By labelDashBoards = By.xpath("//*[contains(text(), 'Dashboards')]");

	private By listaLabelGestores = By.xpath("//*[@class='dropdown-menu']/li/a");

	private By listaLinksPaineis = By.xpath("//*[@class='widget-title']/a");

	private By listaDePaineis = By.xpath("//*[@class='block']");

	private By listaCabecalhosDosPaineis = By.xpath("//*[@class='table table-bordered']/thead/tr");

	private By listaCorpoDosPaineis = By.xpath("//*[@class='table table-bordered']/tbody");

	private By divQualityGate = By.xpath("//*[@id='overview-quality-gate']");
	
	private By issues = By.xpath("//*[@class='active']/a");

	private By vulnerability = By.xpath("(//a[contains(@href,'VULNERABILITY')])[1]");

	private By severity = By.xpath("//*[@data-property='severities']");

	private By listaSeverity = By.xpath("//*[@id='content']/div/div[1]/div[2]/div/div[5]/div");

	private By quantidadeBlockers = By.xpath("//*[@data-value='BLOCKER']/span[2]");

	private By quantidadeCriticals = By.xpath("//*[@data-value='CRITICAL']/span[2]");

	private By quantidadeInfos = By.xpath("//*[@data-value='INFO']/span[2]");

	private By quantidadeMinors = By.xpath("//*[@data-value='MINOR']/span[2]");

	private By quantidadeMajors = By.xpath("//*[@data-value='MAJOR']/span[2]");
	
	private By quantidadeBugs = By.xpath("//*[@data-value='BUG']/span[2]");
	
	private By quantidadeVulnerabilidades = By.xpath("//*[@data-value='VULNERABILITY']/span[2]");
	
	private By quantidadeCodeSmells = By.xpath("//*[@data-value='CODE_SMELL']/span[2]");

	public By getQuantidadeBugs() {
		return quantidadeBugs;
	}

	public By getQuantidadeVulnerabilidades() {
		return quantidadeVulnerabilidades;
	}

	public By getQuantidadeCodeSmells() {
		return quantidadeCodeSmells;
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

	public By getLabelDashBoards() {
		return labelDashBoards;
	}

	public By getListaLabelGestores() {
		return listaLabelGestores;
	}

	public By getListaLinksPaineis() {
		return listaLinksPaineis;
	}

	public By getListaDePaineis() {
		return listaDePaineis;
	}

	public By getListaCabecalhosDosPaineis() {
		return listaCabecalhosDosPaineis;
	}

	public By getListaCorpoDosPaineis() {
		return listaCorpoDosPaineis;
	}

	public By getDivQualityGate() {
		return divQualityGate;
	}

	public By getIssues() {
		return issues;
	}

	public By getVulnerability() {
		return vulnerability;
	}

	public By getSeverity() {
		return severity;
	}

	public By getListaSeverity() {
		return listaSeverity;
	}

	public By getQuantidadeBlockers() {
		return quantidadeBlockers;
	}

	public By getQuantidadeCriticals() {
		return quantidadeCriticals;
	}

	public By getQuantidadeInfos() {
		return quantidadeInfos;
	}

	public By getQuantidadeMinors() {
		return quantidadeMinors;
	}

	public By getQuantidadeMajors() {
		return quantidadeMajors;
	}

}
