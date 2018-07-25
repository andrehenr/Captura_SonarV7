package br.com.rsi.capturaSonarEspanha.steps.business;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import br.com.rsi.capturaSonarEspanha.domain.Sigla;
import br.com.rsi.capturaSonarEspanha.pages.PageObjectClass;
import br.com.rsi.capturaSonarEspanha.util.LerArquivoCSV;
import br.com.rsi.capturaSonarEspanha.util.MassaCaptura;
import io.openbdt.element.WebBrowserScreenElement;
import jxl.common.Logger;
import net.serenity_bdd.core.annotations.findby.By;

@ContextConfiguration("/appcontext.xml")
public class StepBusiness {

	PageObjectClass page;
	private static Logger LOG = Logger.getLogger(StepBusiness.class);

	@Autowired
	private WebBrowserScreenElement viewElement; // OBJETO QUE CONTeM MeTODOS
													// PRoPRIOS DO FRAMEWORK

	public void open(String url) {
		viewElement.open(url);
		viewElement.getDriver().manage().window().maximize();
	}

	public void preencherLogin(String login) {
		viewElement.sendText(page.getCampoLogin(), login);
	}

	public void preencherSenha(String senha) {
		viewElement.sendText(page.getCampoSenha(), senha);
	}

	public void clicarEmLogIn() {
		viewElement.click(page.getBotaoLogin());
	}

	public void clicarEmDashBoards() {
		viewElement.waitForElementIsPresent(2000, page.getLabelDashBoards());
		viewElement.click(page.getLabelDashBoards());
	}
	
	public String capturarQualityGate(){
		viewElement.waitForElementIsPresent(1000, page.getDivQualityGate());
		return viewElement.getText(page.getDivQualityGate()).replaceAll("Quality Gate", "").trim().toUpperCase();
	}
	
	public void clicarEmIssues(){
		viewElement.click(page.getIssues());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} 
	public void clicarEmVulnerability(){
		viewElement.click(page.getVulnerability());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void clicarEmSeverity(){
		viewElement.click(page.getSeverity());
	}
	
	public void clicarGestor(String gestor) {
		// Clico em DashBoards para aparecer os gestores cnfigurados
		clicarEmDashBoards();
		// Entre os gestores configurados eu escoloho o recebido pelo metódo e
		// acesso o painel do mesmo
		for (WebElement elementGestor : page.getListaLabelGestores()) {
			if (elementGestor.getText().equals(gestor)) {
				System.out.println("Acessando Painel do(a) " + elementGestor.getText());
				viewElement.navigate(elementGestor.getAttribute("href"));
				break;
			}
		}

	}

	public void capturaDosPaineis() {
		// Leio a massa de captura
		ArrayList<MassaCaptura> massaCapturaCompleta = (ArrayList) LerArquivoCSV.getInformacoesArquivo();
		// Inicio a captura de linha a linha da massa
		int i = 0;
		while (i < massaCapturaCompleta.size()) {
			try {
				clicarGestor(massaCapturaCompleta.get(i).getGestor());
				selecionaPainel(massaCapturaCompleta.get(i).getPainel());
				i++;
			} catch (Exception e) {
				// Se ocorrer algum erro na captura de um painel eu passo para a
				// proxima linha
				i++;
			}
		}
	}

	private String capturaURLPainel(WebElement linkPainel) {
		String URL = null;
		try {
			URL = linkPainel.getAttribute("href");
			LOG.debug(">> URL Capturada");
		} catch (Exception e) {
			LOG.debug("Erro ao capturar URL");
		}
		return URL;
	}

	private String capturaNomeSigla(int posicaoPainelDentrodoGestor) {

		String nomeSigla = null;
		try {
			nomeSigla = page.getListaDePaineis().get(posicaoPainelDentrodoGestor).findElement(By.tagName("h3"))
					.getText().toString();
			System.out.println(">> Nome da Sigla Capturada");
		}

		catch (Exception e) {
			LOG.debug("Erro ao capturar Nome da Sigla");
		}

		return nomeSigla;

	}

	// Retorno um array de String onde na primeira posicao tem a data no formato
	// de String e na segunda posicao tem a String com data e versao juntas
	public String[] separaDataDaVersao(int posicaoPainelDentrodoGestor) {

		// Esta lista comporta o cabecalho com a sigla do painel e as
		// datas e versões das rodadas do sonar
		List<WebElement> listaDeTH = page.getListaCabecalhosDosPaineis().get(posicaoPainelDentrodoGestor)
				.findElements(By.tagName("th"));
		String thComDataVersao = listaDeTH.get(listaDeTH.size() - 1).getText().trim().toString();

		// Código necessário para separar a data da rodada da versao
		String regex = "\\d{2}\\/\\d{2}\\/\\d{4}";

		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(thComDataVersao);

		String[] array = { "", "" };

		while (matcher.find()) {
			array[0] = matcher.group(0);
			array[1] = thComDataVersao;
		}

		return array;

	}

	private Sigla capturaElementosNoPainel(int posicaoPainelDentrodoGestor){
		Sigla sigla = null;
		
		try{
			sigla = new Sigla();
			//Capturo em uma lista todas as linhas a partir de Lines of Code
			ArrayList<WebElement> listaDeLinhasDoCorpoPainel = (ArrayList) page.getListaCorpoDosPaineis().get(posicaoPainelDentrodoGestor).findElements(By.tagName("tr"));
			
			int quantidadeRodadasSonar = listaDeLinhasDoCorpoPainel.get(0).findElements(By.tagName("td")).size();
			
			for(WebElement linhas : listaDeLinhasDoCorpoPainel){
				
				String linhaAnalisada = linhas.getText().toString();
				String textoDaUltimaColuna = linhas.findElements(By.tagName("td")).get(quantidadeRodadasSonar-1).getText().toString();
				if(!textoDaUltimaColuna.isEmpty()){
					if(linhaAnalisada.contains("Lines of Code")){
						sigla.setLinhaCodigo(Integer.parseInt(textoDaUltimaColuna));
					}
					if(linhaAnalisada.contains("Bugs")){
						sigla.setBugs(Integer.parseInt(textoDaUltimaColuna));
					}
					if(linhaAnalisada.contains("Code Smells")){
						sigla.setCodeSmall(Integer.parseInt(textoDaUltimaColuna));
					}
					if(linhaAnalisada.contains("Vulnerabilities")){
						sigla.setVulnerabilidades(Integer.parseInt(textoDaUltimaColuna));
					}
					if(linhaAnalisada.contains("Coverage")){
						sigla.setCobertura(textoDaUltimaColuna);
					}
					if(linhaAnalisada.contains("Technical Debt")){
						sigla.setDebitoTecnico(textoDaUltimaColuna);
					}
					if(linhaAnalisada.contains("Reliability Remediation Effort")){
						sigla.setEsforcoConfiabilidade(textoDaUltimaColuna);
					}
					if(linhaAnalisada.contains("Security Remediation Effort")){
						sigla.setEsforcoSeguranca(textoDaUltimaColuna);
					}
					
				}
			}
			
		}
		catch(Exception e){
			LOG.error("Erro ao capturar elementos no painel");
		}
		
		return sigla;
	}

	private Sigla selecionaPainel(String painel) {
		List<WebElement> linksPaineis = page.getListaLinksPaineis();
		int i = 0;
		Sigla siglaCapturada = null;
		boolean encontrarPainel = false;
		System.out.println(" - Painel a ser selecionado " + painel);
		for (WebElement linkPainel : linksPaineis) {
			if (linkPainel.getText().toString().trim().equals(painel.toUpperCase())) {
				encontrarPainel = true;
				System.out.println(">> Painel  " + painel + " encontrado!");
				siglaCapturada = new Sigla();
				// Dentro da lista de Nomes dos Paineis (linksPaineis) é
				// possível capturar o link do painel coletando o atributo href
				siglaCapturada.setUrl(capturaURLPainel(linkPainel));
				siglaCapturada.setSigla(capturaNomeSigla(i));

				String[] arrayDoMetodoSeparaDataDaVersao = separaDataDaVersao(i);

				String dataEncontrada = arrayDoMetodoSeparaDataDaVersao[0];
				String stringCompleta = arrayDoMetodoSeparaDataDaVersao[1];

				Date dataSonar = validadorData(dataEncontrada, "Data sonar");

				// Coleta a string completo e retiro a data deixando apenas a
				// versão
				String versao = stringCompleta.replace(dataEncontrada, "");
				LOG.debug(">> Versao Localizada");

				siglaCapturada.setDataSonar(dataSonar);
				siglaCapturada.setVersao(versao);

				// Capturo o resto do elementos que estao no painel
				Sigla novaSigla = capturaElementosNoPainel(i);
				
				siglaCapturada.setLinhaCodigo(novaSigla.getLinhaCodigo());
				LOG.debug(">> Linhas de Codigo Capturada");
				siglaCapturada.setBugs(novaSigla.getBugs());
				LOG.debug(">> Bugs Capturado");
				siglaCapturada.setCodeSmall(novaSigla.getCodeSmall());
				LOG.debug(">> Code Smell Capturado");
				siglaCapturada.setVulnerabilidades(novaSigla.getVulnerabilidades());
				LOG.debug(">> Vulnerabilidades Capturada");
				siglaCapturada.setCobertura(novaSigla.getCobertura());
				LOG.debug(">> Cobertura Capturada");
				siglaCapturada.setDebitoTecnico(novaSigla.getDebitoTecnico());
				LOG.debug(">> Debito Tecnico Capturado");
				siglaCapturada.setEsforcoConfiabilidade(novaSigla.getEsforcoConfiabilidade());
				LOG.debug(">> Esforco de Confiabilidade Capturado");
				siglaCapturada.setEsforcoSeguranca(novaSigla.getEsforcoSeguranca());
				LOG.debug(">> Esforco de Seguranca Capturado");
				
				navegarParaDentroDoPainel(siglaCapturada.getUrl());
				
				siglaCapturada.setQualidade(capturarQualityGate());

				System.out.println(siglaCapturada.toString());

				break;
			}
			i++;
		}
		if (!encontrarPainel) {
			LOG.error("Erro ao localizar Painel " + painel);
		}
		return siglaCapturada;
	}
	
	public void navegarParaDentroDoPainel(String urlPainel){
		viewElement.navigate(urlPainel);
		LOG.debug(">> Acessando o Painel");
	}

	public static Date validadorData(String dataInfo, String msg) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date dataFinal = new Date();
		if (dataInfo.isEmpty()) {
			dataFinal = null;
		} else {
			try {
				dataFinal = (Date) formatter.parse(dataInfo);
				LOG.debug(">> Data Sonar Validada");
			} catch (ParseException e) {
				dataFinal = null;
			}
		}
		return dataFinal;
	}

}
