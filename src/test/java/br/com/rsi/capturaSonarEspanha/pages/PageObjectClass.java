package br.com.rsi.capturaSonarEspanha.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.pages.PageObject;

public class PageObjectClass extends PageObject {

	@FindBy(id = "login")
	private WebElement campoLogin;

	@FindBy(id = "password")
	private WebElement campoSenha;

	@FindBy(xpath = "//*[@name='commit']")
	private WebElement botaoLogin;

	@FindBy(xpath = "//*[contains(text(), 'Dashboards')]")
	private WebElement labelDashBoards;

	@FindBy(xpath = "//*[@class='dropdown-menu']/li/a")
	private List<WebElement> listaLabelGestores;

	@FindBy(xpath = "//*[@class='widget-title']/a")
	private List<WebElement> listaLinksPaineis;

	@FindBy(xpath = "//*[@class='block']")
	private List<WebElement> listaDePaineis;

	@FindBy(xpath = "//*[@class='table table-bordered']/thead/tr")
	private List<WebElement> listaCabecalhosDosPaineis;

	@FindBy(xpath = "//*[@class='table table-bordered']/tbody")
	private List<WebElement> listaCorpoDosPaineis;

	@FindBy(xpath = "//*[@id='overview-quality-gate']")
	private WebElement divQualityGate;

	@FindBy(xpath="(//*[@class='nav navbar-nav nav-tabs']/li/a)[2]")
	private WebElement issues;
	
	@FindBy(xpath="//*[@id='content']/div/div[1]/div[2]/div/div[3]/div/a[2]")
	private WebElement vulnerability;
	
	@FindBy(xpath="//*[@id='content']/div/div[1]/div[2]/div/div[5]/a")
	private WebElement severity;
	
	@FindBy(xpath="//*[@id='content']/div/div[1]/div[2]/div/div[5]/div")
	private WebElement listaSeverity;
	
	public WebElement getListaSeverity(){
		return severityBlocker;
	}
	
	public WebElement getListaSeverity(){
		return severityCritical;
	}
	public WebElement getListaSeverity(){
		return SeverityBlocker;
	}
	public WebElement getListaSeverity(){
		return SeverityBlocker;
	}
	
	
	public WebElement getSeverity(){
		return severity;
	}
	
	public WebElement getVulnerability(){
		return vulnerability;
	}
	public WebElement getIssues(){
		return issues;
	}
	
	public WebElement getDivQualityGate() {
		return divQualityGate;
	}

	public List<WebElement> getListaCorpoDosPaineis() {
		return listaCorpoDosPaineis;
	}

	public List<WebElement> getListaCabecalhosDosPaineis() {
		return listaCabecalhosDosPaineis;
	}

	public List<WebElement> getListaLinksPaineis() {
		return listaLinksPaineis;
	}

	public List<WebElement> getListaDePaineis() {
		return listaDePaineis;
	}

	public WebElement getLabelDashBoards() {
		return labelDashBoards;
	}

	public List<WebElement> getListaLabelGestores() {
		return listaLabelGestores;
	}

	public WebElement getBotaoLogin() {
		return botaoLogin;
	}

	public WebElement getCampoLogin() {
		return campoLogin;
	}

	public WebElement getCampoSenha() {
		return campoSenha;
	}

}
