package br.com.rsi.capturaSonarEspanha.steps.definition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.test.context.ContextConfiguration;

import br.com.rsi.capturaSonarEspanha.domain.Sigla;
import br.com.rsi.capturaSonarEspanha.steps.business.StepBusiness;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

@ContextConfiguration("/appcontext.xml")
public class StepDefinition {

	@Steps
	private StepBusiness stepBusiness;

	public static Properties getProperties() {

		Properties dadosDB = new Properties();

		try {
			FileInputStream arquivoDadosProperties = null;
			try {
				arquivoDadosProperties = new FileInputStream("./properties/dados.properties");
			} catch (FileNotFoundException e) {
				System.out.println("Erro com o arquivo dados.properties : " + e.getMessage());
			}
			// Instancio um Properties
			dadosDB.load(arquivoDadosProperties);
		} catch (IOException e) {
			System.out.println("Erro com ao carregar o arquivo dados.properties : " + e.getMessage());
		}

		return dadosDB;

	}

	@Given("^estou na pagina inicial do site$")
	public void estou_na_pagina_inicial_do_site() {
		stepBusiness.open(getProperties().getProperty("prop.server.host"));
	}

	@When("^preencho login$")
	public void preencho_login() {
		stepBusiness.preencherLogin(getProperties().getProperty("prop.server.login"));
	}

	@When("^preencho senha$")
	public void preencho_senha() {
		stepBusiness.preencherSenha(getProperties().getProperty("prop.server.password"));
	}

	@When("^pressiono botao entrar")
	public void pressiono_botao_entrar() {
		stepBusiness.clicarEmLogIn();
	}
	
	@When("^inicio a captura dos paineis listados na massa")
	public void inicio_a_captura_dos_paineis_listados_na_massa(){
		stepBusiness.iniciarCapturaDosPaineis();
	}
	
}
