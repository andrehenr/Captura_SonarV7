package br.com.rsi.capturaSonarEspanha.exe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.rsi.capturaSonarEspanha.domain.AnaliseCodigo;
import br.com.rsi.capturaSonarEspanha.pages.PageObjectClass;
import br.com.rsi.capturaSonarEspanha.util.LerArquivoCSV;
import br.com.rsi.capturaSonarEspanha.util.MassaCaptura;
import br.com.rsi.novaCaptura.dao.AnaliseCodigoEspanhaDAO;
import jxl.common.Logger;

public class Executora {
	public static void main(String[] args) {

		ChromeDriverService service = null;
		WebDriver driver = null;

		try {
			service = new ChromeDriverService.Builder().usingDriverExecutable(new File(".//driver//chromedriver.exe"))
					.usingAnyFreePort().build();
			try {
				service.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Properties dadosDB = getProperties();
			driver = new ChromeDriver(service);

			open(driver, dadosDB.getProperty("prop.server.host"));
			preencherLogin(driver, dadosDB.getProperty("prop.server.login"));
			preencherSenha(driver, dadosDB.getProperty("prop.server.password"));
			clicarEmLogIn(driver);
			iniciarCapturaDosPaineis(driver);

		} catch (Exception e) {
			LOG.debug("Erro ao capturar siglas devido a : " + e.getMessage());
		} finally {
			driver.close();
			driver.quit();
			service.stop();
			AnaliseCodigoEspanhaDAO.fecharConexao();
		}

	}
	// Termino da metodo main

	private static Logger LOG = Logger.getLogger(Executora.class);

	private static AnaliseCodigo sigla; // Objeto utilizado para a persistencia
										// de cada
	// painel
	// da massa de captura

	private static FileWriter arq;
	private static StringBuilder log;
	private static PrintWriter gravarArq;

	private static void criarLogCaptura() {

		try {
			if (arq == null) {
				Calendar dataAtual = Calendar.getInstance();
				String nomeLog = getProperties().getProperty("prop.logs").toString() + "Captura_"
						+ dataAtual.get(Calendar.DAY_OF_MONTH) + (dataAtual.get(Calendar.MONTH) + 1)
						+ dataAtual.get(Calendar.YEAR) + "_" + dataAtual.get(Calendar.HOUR_OF_DAY)
						+ dataAtual.get(Calendar.MINUTE) + dataAtual.get(Calendar.SECOND);
				arq = new FileWriter(nomeLog + ".txt");
			}

		} catch (Exception e) {
			LOG.error("Falha ao criar o arquivo de log");
		}
	}

	public static void escreverLog() {
		try {
			if (gravarArq == null) {
				gravarArq = new PrintWriter(arq);
			}
			gravarArq.append(log + "\r\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (arq != null) {
				try {
					gravarArq.close();
					arq.close();
				} catch (IOException e) {
					LOG.error("Falha ao escrever no log");
				}
			}
		}
	}

	public static void open(WebDriver driver, String url) {
		driver.navigate().to(url);
		driver.manage().window().maximize();
	}

	public static Properties getProperties() {

		Properties dadosDB = new Properties();

		try {
			FileInputStream arquivoDadosProperties = null;
			try {
				arquivoDadosProperties = new FileInputStream("./properties/dados.properties");
			} catch (FileNotFoundException e) {
				LOG.error("Erro com o arquivo dados.properties : " + e.getMessage());
			}
			// Instancio um Properties
			dadosDB.load(arquivoDadosProperties);
		} catch (IOException e) {
			LOG.debug("Erro com ao carregar o arquivo dados.properties : " + e.getMessage());
		}

		return dadosDB;

	}

	public static void preencherLogin(WebDriver driver, String login) {
		new WebDriverWait(driver, 2000)
				.until(ExpectedConditions.presenceOfElementLocated(new PageObjectClass().getCampoLogin()));
		driver.findElement(new PageObjectClass().getCampoLogin()).sendKeys(login);
	}

	public static void preencherSenha(WebDriver driver, String senha) {
		WebElement senhaElement = driver.findElement(new PageObjectClass().getCampoSenha());
		senhaElement.click();
		senhaElement.sendKeys(senha);
	}

	public static void clicarEmLogIn(WebDriver driver) {
		driver.findElement(new PageObjectClass().getBotaoLogin()).click();
	}

	public static void iniciarCapturaDosPaineis(WebDriver driver) {
		try {
			// Cria arquivo de log
			criarLogCaptura();
			// Inicia o objeto que registrara o log dos paineis
			log = new StringBuilder();
			// Leio a massa de captura
			List<MassaCaptura> massaCapturaCompleta = LerArquivoCSV.getInformacoesArquivo();
			// Inicio a captura de linha a linha da massa
			int i = 0;
			while (i < massaCapturaCompleta.size()) {
				try {
					sigla = new AnaliseCodigo();
					selecionaPainel(massaCapturaCompleta.get(i).getPainel(), driver);
					// // Entro na pagina do painel
					// navegarParaDentroDoPainel(driver);
					// // Capturo o Quality Gate
					// capturarQualityGate(driver);
					// // Clico em Vulnerability para poder capturar as
					// // vulnerabilidades.
					// clicarEmVulnerability(driver);
					// clicarEmSeverity(driver);
					// // Capturo as vulnerabilidades de acordo com a severidade
					// capturarVulnerabilidades(driver);
					// // Clico na opcao Issues para poder capturar as Issues do
					// // Painel
					// clicarEmIssues(driver);
					// // Clico em Severity para coletar as Issues de acordo com
					// a
					// // Severity
					// clicarEmSeverity(driver);
					// // Capturo as Issues
					// capturarIssues(driver);
					// // Insiro nome do Gestor vindo da massa
					// inserirNomeGestor(massaCapturaCompleta.get(i).getGestor());
					// // Inserir data da captura
					// inserirDataCaptura();
					// // Inserir descricao vindo do properties
					// inserirDescricao();
					// // Inserir nome do Projeto no objeto sigla.
					// inserirNomeProjeto(massaCapturaCompleta.get(i).getPainel());
					// // System.out.println("Sigla: " + sigla.toString());
					// // salvarSiglaBancoDeDados();
					// // LOG.debug("Exemplo de log: " + log);

				} catch (Exception e) {
					// Se ocorrer algum erro na captura de um painel eu passo
					// para a proxima linha
					LOG.debug("Erro ao capturar: " + massaCapturaCompleta.get(i).getPainel());
					log.append("Erro ao capturar: " + massaCapturaCompleta.get(i).getPainel() + "\n");

				} finally {
					i++;
				}
			}

			escreverLog();

		} catch (Exception e) {
			LOG.debug("Erro ao capturar siglas");
			log.append("Erro ao capturar siglas");
		}

	}

	private static void selecionaPainel(String painel, WebDriver driver) {
		By xpathCampoPesquisaPaineis = new PageObjectClass().getCampoPesquisaPainel();
		new WebDriverWait(driver, 2000).until(ExpectedConditions.presenceOfElementLocated(xpathCampoPesquisaPaineis));
		WebElement campoPesquisaPainel = driver.findElement(xpathCampoPesquisaPaineis);
		System.out.println(painel);
		campoPesquisaPainel.sendKeys(painel);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		By xpathLinkPainel = By.xpath("//a[contains(.,'" + painel + "')]");
		driver.findElement(xpathLinkPainel).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		capturaCobertura(driver);
		By xpathBotaoProjeto = new PageObjectClass().getBotaoProjetos();
		driver.findElement(xpathBotaoProjeto).click();
	}

	private static void capturaCobertura(WebDriver driver) {
		String cobertura = driver.findElement(new PageObjectClass().getLabelCobertura()).getText().trim().toString();
		System.out.println(cobertura);
		sigla.setCobertura(cobertura);
	}

	// public static void clicarEmDashBoards(WebDriver driver) {
	// By dashboard = new PageObjectClass().getLabelDashBoards();
	// new WebDriverWait(driver,
	// 2000).until(ExpectedConditions.presenceOfElementLocated(dashboard));
	// driver.findElement(dashboard).click();
	// }
	//
	// public static void capturarQualityGate(WebDriver driver) {
	// By divQualityGate = new PageObjectClass().getDivQualityGate();
	// new WebDriverWait(driver,
	// 1000).until(ExpectedConditions.presenceOfElementLocated(divQualityGate));
	// sigla.setQualidade(
	// driver.findElement(divQualityGate).getText().replaceAll("Quality Gate",
	// "").trim().toUpperCase());
	// LOG.debug(">> Quality Gate capturado");
	// log.append("Quality Gate capturado \n");
	// }
	//
	// public static void clicarEmIssues(WebDriver driver) {
	// try {
	// WebElement linkIssues = driver.findElement(new
	// PageObjectClass().getIssues());
	// driver.navigate().to(linkIssues.getAttribute("href"));
	// // Esperar por 20 segndos, ate o elemento que possui o total de
	// // Code Smells apareca no arquivo DOM da pagina, isso me garante que a
	// pagina recarregou.
	// new WebDriverWait(driver, 20000).until(
	// ExpectedConditions.presenceOfElementLocated(new
	// PageObjectClass().getQuantidadeCodeSmells()));
	// } catch (Exception e) {
	// LOG.debug("Erro ao capturar link issues");
	// }
	// LOG.debug(">> Clicando em Issues \n");
	// }
	//
	// public static void clicarEmVulnerability(WebDriver driver) {
	// WebElement linkPainelDeVulnerabilidades = driver.findElement(new
	// PageObjectClass().getVulnerability());
	// driver.navigate().to(linkPainelDeVulnerabilidades.getAttribute("href"));
	// LOG.debug(">> Clicando em vulnerabilidades");
	// // Esperar por 20 segndos, ate o elemento que possui o total de
	// // vuneralidades aparecer no arquivo DOM da pagina
	// new WebDriverWait(driver, 20000).until(
	// ExpectedConditions.presenceOfElementLocated(new
	// PageObjectClass().getQuantidadeVulnerabilidades()));
	//
	// }
	//
	// public static void clicarEmSeverity(WebDriver driver) {
	// PageObjectClass page = new PageObjectClass();
	// By checkBoxSeverity = page.getSeverity();
	// driver.findElement(checkBoxSeverity).click();
	// // Elemento que define a severidade critica para vulnerabilidades e
	// // issues
	// By elementoQuantidadeCriticals = page.getQuantidadeCriticals();
	// new WebDriverWait(driver, 10000)
	// .until(ExpectedConditions.presenceOfElementLocated(elementoQuantidadeCriticals));
	//
	// LOG.debug(">> Clicando em Severity \n");
	// }
	//
	// public static void capturaDosPaineis(WebDriver driver) {
	// // Leio a massa de captura
	// List<MassaCaptura> massaCapturaCompleta =
	// LerArquivoCSV.getInformacoesArquivo();
	// // Inicio a captura de linha a linha da massa
	// int i = 0;
	// while (i < massaCapturaCompleta.size()) {
	// try {
	// clicarGestor(massaCapturaCompleta.get(i).getGestor(), driver);
	// AnaliseCodigo sigla =
	// selecionaPainel(massaCapturaCompleta.get(i).getPainel(), driver);
	// // log.append(sigla.toString() + "\n");
	// LOG.debug(sigla.toString());
	// i++;
	// } catch (Exception e) {
	// // Se ocorrer algum erro na captura de um painel eu passo para a
	// // proxima linha
	// i++;
	// }
	// }
	// }
	//
	// private static String capturaURLPainel(WebElement linkPainel) {
	// String URL = null;
	// try {
	// URL = linkPainel.getAttribute("href");
	// LOG.debug(">> URL Capturada");
	// log.append("URL Capturada\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao capturar URL");
	// log.append("Erro ao capturar URL \n");
	// }
	// return URL;
	// }
	//
	// private static String capturaNomeSigla(int posicaoPainelDentrodoGestor,
	// WebDriver driver) {
	//
	// String nomeSigla = null;
	// try {
	// List<WebElement> listaDePaineisGestores = driver.findElements(new
	// PageObjectClass().getListaDePaineis());
	// nomeSigla =
	// listaDePaineisGestores.get(posicaoPainelDentrodoGestor).findElement(By.tagName("h3")).getText()
	// .toString();
	// LOG.debug(">> Nome da Sigla Capturada");
	// log.append("Nome da Sigla Capturada\n");
	// }
	//
	// catch (Exception e) {
	// LOG.debug("Erro ao capturar Nome da Sigla");
	// log.append("Erro ao capturar Nome da Sigla\n");
	// }
	//
	// return nomeSigla;
	//
	// }
	//
	// // Retorno um array de String onde na primeira posicao tem a data no
	// formato
	// // de String e na segunda posicao tem a String com data e versao juntas
	// public static String[] separaDataDaVersao(int
	// posicaoPainelDentrodoGestor, WebDriver driver) {
	//
	// // Esta lista comporta o cabecalho com a sigla do painel e as
	// // datas e versões das rodadas do sonar
	// List<WebElement> listaCabecalhosDosPaineis = driver
	// .findElements(new PageObjectClass().getListaCabecalhosDosPaineis());
	// List<WebElement> listaDeTH =
	// listaCabecalhosDosPaineis.get(posicaoPainelDentrodoGestor)
	// .findElements(By.tagName("th"));
	// String thComDataVersao = listaDeTH.get(listaDeTH.size() -
	// 1).getText().trim().toString();
	//
	// // Código necessário para separar a data da rodada da versao
	// String regex = "\\d{2}\\/\\d{2}\\/\\d{4}";
	//
	// Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
	// Matcher matcher = pattern.matcher(thComDataVersao);
	//
	// String[] array = { "", "" };
	//
	// while (matcher.find()) {
	// array[0] = matcher.group(0);
	// array[1] = thComDataVersao;
	// }
	//
	// return array;
	//
	// }
	//
	// private static AnaliseCodigo capturaElementosNoPainel(int
	// posicaoPainelDentrodoGestor, WebDriver driver) {
	// AnaliseCodigo sigla = null;
	//
	// try {
	// sigla = new AnaliseCodigo();
	// // Capturo em uma lista todas as linhas a partir de Lines of Code
	// List<WebElement> listaBodyDosPaineis = driver.findElements(new
	// PageObjectClass().getListaCorpoDosPaineis());
	// List<WebElement> listaDeLinhasDoCorpoPainel =
	// listaBodyDosPaineis.get(posicaoPainelDentrodoGestor)
	// .findElements(By.tagName("tr"));
	//
	// int quantidadeRodadasSonar =
	// listaDeLinhasDoCorpoPainel.get(0).findElements(By.tagName("td")).size();
	//
	// // LOG.debug(quantidadeRodadasSonar);
	//
	// for (WebElement linhas : listaDeLinhasDoCorpoPainel) {
	//
	// String linhaAnalisada = linhas.getText().toString();
	// // LOG.debug("linha Analisada: " + linhaAnalisada);
	// String textoDaUltimaColuna =
	// linhas.findElements(By.tagName("td")).get(quantidadeRodadasSonar - 1)
	// .getText().toString();
	// // LOG.debug("texto Ultima Coluna: " + textoDaUltimaColuna);
	// String textoCapturado = new String();
	// if (textoDaUltimaColuna == null || textoDaUltimaColuna.equals("")) {
	// textoCapturado = "0";
	// } else {
	// textoCapturado = textoDaUltimaColuna;
	// }
	//
	// if (linhaAnalisada.contains("Lines of Code")) {
	// sigla.setLinhaCodigo(Integer.parseInt(validaStringComPonto(textoCapturado)));
	// }
	// // if (linhaAnalisada.contains("Bugs")) {
	// // sigla.setBugs(Integer.parseInt(validaStringComPonto(textoCapturado)));
	// // }
	// // if (linhaAnalisada.contains("Code Smells")) {
	// //
	// sigla.setCodeSmells(Integer.parseInt(validaStringComPonto(textoCapturado)));
	// // }
	// // if (linhaAnalisada.contains("Vulnerabilities")) {
	// //
	// sigla.setVulnerabilidades(Integer.parseInt(validaStringComPonto(textoCapturado)));
	// // }
	// if (linhaAnalisada.contains("Coverage")) {
	// sigla.setCobertura(textoCapturado);
	// }
	// if (linhaAnalisada.contains("Technical Debt")) {
	// sigla.setDebitoTecnico(textoCapturado);
	// }
	// if (linhaAnalisada.contains("Reliability Remediation Effort")) {
	// sigla.setEsforcoConfiabilidade(textoCapturado);
	// }
	// if (linhaAnalisada.contains("Security Remediation Effort")) {
	// sigla.setEsforcoSeguranca(textoCapturado);
	// }
	// }
	//
	// } catch (Exception e) {
	// e.getStackTrace();
	// LOG.error("Erro ao capturar elementos no painel");
	// log.append("Erro ao capturar elementos no painel\n");
	// }
	//
	// return sigla;
	// }
	//
	// public static String validaStringComPonto(String textoCapturado) {
	// // LOG.debug("TEXTO RECEBIDO " + textoCapturado);
	// StringBuilder juncaoDaLinha = new StringBuilder();
	// if (textoCapturado.contains("k")) {
	// String resultado = textoCapturado.replace("k", "");
	// Double multiplicacao = Double.parseDouble(resultado) * 1000;
	// juncaoDaLinha = new StringBuilder(multiplicacao.intValue() + "");
	// } else if (textoCapturado.contains(".")) {
	// String[] linhaDeCodigo = textoCapturado.split("\\.");
	// // LOG.debug("Array de String: " + linhaDeCodigo);
	// for (String linha : linhaDeCodigo) {
	// // LOG.debug("Linha: " + linha);
	// juncaoDaLinha.append(linha);
	// // LOG.debug("Junção: " + juncaoDaLinha);
	// }
	// } else {
	// juncaoDaLinha = new StringBuilder(textoCapturado);
	// }
	// // LOG.debug("Junção Completa :" + juncaoDaLinha.toString());
	// return juncaoDaLinha.toString();
	// }
	//
	// /*
	// * Metodo utilizado para coletar os dados no painel (Table History)
	// *
	// * Retorna um objeto do tipo Sigla com a Data do Sonar, Sigla, URL do
	// * Painel, versão da rodada do sonar, Linhas de Codigo, Bugs, Code Smell,
	// * Vulnerabilidades, Cobertura, Debito Tecnico, Esforço de Confiabilidade,
	// * Esforço de Segurança e Quality Gate
	// *
	// */
	// private static void selecionaPainel(String painel, WebDriver driver) {
	// WebElement campoPesquisaPainel = driver.findElement(new
	// PageObjectClass().getCampoPesquisaPainel());
	//
	// campoPesquisaPainel.click();
	// campoPesquisaPainel.sendKeys(painel);
	// campoPesquisaPainel.submit();
	//
	// WebElement painelAtual =
	// driver.findElement(By.xpath("//a[contains(.,'"+painel+"')]"));
	//
	// painelAtual.click();
	//
	//// List<WebElement> linksPaineis = driver.findElements(new
	// PageObjectClass().getListaLinksPaineis());
	//// int i = 0;
	//// AnaliseCodigo siglaCapturada = null;
	//// boolean encontrarPainel = false;
	//// for (WebElement linkPainel : linksPaineis) {
	//// if
	// (linkPainel.getText().toString().trim().equals(painel.toUpperCase())) {
	//// encontrarPainel = true;
	//// String logPainelEncontrado = "Painel " + painel + "\n";
	//// log.append("----------------------\n");
	//// log.append(logPainelEncontrado);
	//// log.append("----------------------\n");
	//// LOG.debug("--------------------------------------");
	//// LOG.debug(">> " + logPainelEncontrado.replace("\n", ""));
	//// LOG.debug("--------------------------------------");
	//// siglaCapturada = new AnaliseCodigo();
	//// // Dentro da lista de Nomes dos Paineis (linksPaineis) é
	//// // possível capturar o link do painel coletando o atributo href
	//// siglaCapturada.setUrl(capturaURLPainel(linkPainel));
	//// siglaCapturada.setSigla(capturaNomeSigla(i, driver));
	////
	//// String[] arrayDoMetodoSeparaDataDaVersao = separaDataDaVersao(i,
	// driver);
	////
	//// String dataEncontrada = arrayDoMetodoSeparaDataDaVersao[0];
	//// String stringCompleta = arrayDoMetodoSeparaDataDaVersao[1];
	////
	//// Date dataSonar = validadorData(dataEncontrada, "Data sonar");
	////
	//// // Coleta a string completo e retiro a data deixando apenas a
	//// // versão
	//// String versao = stringCompleta.replace(dataEncontrada, "");
	////
	//// siglaCapturada.setDataSonar(dataSonar);
	//// siglaCapturada.setVersao(versao.trim());
	//// LOG.debug(">> Versao Localizada");
	//// log.append("Versao Localizada\n");
	////
	//// // Capturo o resto do elementos que estao no painel
	//// AnaliseCodigo novaSigla = capturaElementosNoPainel(i, driver);
	////
	//// siglaCapturada.setLinhaCodigo(novaSigla.getLinhaCodigo());
	//// LOG.debug(">> Linhas de Codigo Capturada");
	//// log.append("Linhas de Codigo Capturada\n");
	//// // siglaCapturada.setBugs(novaSigla.getBugs());
	//// // LOG.debug(">> Bugs Capturado");
	//// // log.append("Bugs Capturado\n");
	//// // siglaCapturada.setCodeSmells(novaSigla.getCodeSmells());
	//// // LOG.debug(">> Code Smell Capturado");
	//// // log.append("Code Smell Capturado\n");
	//// // siglaCapturada.setVulnerabilidades(novaSigla.getVulnerabilidades());
	//// // LOG.debug(">> Vulnerabilidades Capturada");
	//// // log.append("Vulnerabilidades Capturada\n");
	//// siglaCapturada.setCobertura(novaSigla.getCobertura());
	//// LOG.debug(">> Cobertura Capturada");
	//// log.append("Cobertura Capturada\n");
	//// siglaCapturada.setDebitoTecnico(novaSigla.getDebitoTecnico());
	//// LOG.debug(">> Debito Tecnico Capturado");
	//// log.append("Debito Tecnico Capturado\n");
	//// siglaCapturada.setEsforcoConfiabilidade(novaSigla.getEsforcoConfiabilidade());
	//// LOG.debug(">> Esforco de Confiabilidade Capturado");
	//// log.append("Esforco de Confiabilidade Capturado\n");
	//// siglaCapturada.setEsforcoSeguranca(novaSigla.getEsforcoSeguranca());
	//// LOG.debug(">> Esforco de Seguranca Capturado");
	//// log.append("Esforco de Seguranca Capturado\n");
	//// break;
	//// }
	//// i++;
	//// }
	//// if (!encontrarPainel) {
	//// LOG.error("Erro ao localizar Painel " + painel);
	//// log.append("Erro ao localizar Painel\n" + painel);
	//// }
	//// return siglaCapturada;
	// }
	//
	// public static void navegarParaDentroDoPainel(WebDriver driver) {
	// driver.navigate().to(sigla.getUrl());
	// LOG.debug(">> Acessando o Painel");
	// }
	//
	// public static Date validadorData(String dataInfo, String msg) {
	// DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	// Date dataFinal = new Date();
	// if (dataInfo.isEmpty()) {
	// dataFinal = null;
	// } else {
	// try {
	// dataFinal = (Date) formatter.parse(dataInfo);
	// LOG.debug(">> Data Sonar Validada");
	// } catch (ParseException e) {
	// dataFinal = null;
	// }
	// }
	// return dataFinal;
	// }
	//
	// public static void iniciarCapturaDosPaineis(WebDriver driver) {
	// try {
	// // Cria arquivo de log
	// criarLogCaptura();
	// // Inicia o objeto que registrara o log dos paineis
	// log = new StringBuilder();
	// // Leio a massa de captura
	// List<MassaCaptura> massaCapturaCompleta =
	// LerArquivoCSV.getInformacoesArquivo();
	// // Inicio a captura de linha a linha da massa
	// int i = 0;
	// while (i < massaCapturaCompleta.size()) {
	// try {
	// sigla = new AnaliseCodigo();
	// sigla = selecionaPainel(massaCapturaCompleta.get(i).getPainel(), driver);
	// // Entro na pagina do painel
	// navegarParaDentroDoPainel(driver);
	// // Capturo o Quality Gate
	// capturarQualityGate(driver);
	// // Clico em Vulnerability para poder capturar as
	// // vulnerabilidades.
	// clicarEmVulnerability(driver);
	// clicarEmSeverity(driver);
	// // Capturo as vulnerabilidades de acordo com a severidade
	// capturarVulnerabilidades(driver);
	// // Clico na opcao Issues para poder capturar as Issues do
	// // Painel
	// clicarEmIssues(driver);
	// // Clico em Severity para coletar as Issues de acordo com a
	// // Severity
	// clicarEmSeverity(driver);
	// // Capturo as Issues
	// capturarIssues(driver);
	// // Insiro nome do Gestor vindo da massa
	// inserirNomeGestor(massaCapturaCompleta.get(i).getGestor());
	// // Inserir data da captura
	// inserirDataCaptura();
	// // Inserir descricao vindo do properties
	// inserirDescricao();
	// // Inserir nome do Projeto no objeto sigla.
	// inserirNomeProjeto(massaCapturaCompleta.get(i).getPainel());
	// // System.out.println("Sigla: " + sigla.toString());
	//// salvarSiglaBancoDeDados();
	// // LOG.debug("Exemplo de log: " + log);
	//
	// } catch (Exception e) {
	// // Se ocorrer algum erro na captura de um painel eu passo
	// // para a proxima linha
	// LOG.debug("Erro ao capturar: " +
	// massaCapturaCompleta.get(i).getPainel());
	// log.append("Erro ao capturar: " + massaCapturaCompleta.get(i).getPainel()
	// + "\n");
	//
	// } finally {
	// i++;
	// }
	// }
	//
	// escreverLog();
	//
	// } catch (Exception e) {
	// LOG.debug("Erro ao capturar siglas");
	// log.append("Erro ao capturar siglas");
	// }
	//
	// }
	//
	// private static void inserirNomeProjeto(String painel) {
	// sigla.setNomeProjeto(painel);
	// }
	//
	// private static void inserirDescricao() {
	// try {
	// Properties arquivo = getProperties();
	// sigla.setDescricao(arquivo.getProperty("prop.server.descricao"));
	// LOG.debug("Descricao inserida");
	// log.append("Descricao inserida\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao inserir descricao");
	// log.append("Erro ao inserir descricao\n");
	// }
	// }
	//
	// private static void inserirDataCaptura() {
	// try {
	// Date dataAtual = Calendar.getInstance().getTime();
	// sigla.setDataCaptura(dataAtual);
	// LOG.debug("Data de Captura inserida");
	// log.append("Data de Captura inserida\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao inserir data de Captura");
	// log.append("Erro ao inserir data de Captura \n");
	// }
	// }
	//
	// private static void inserirNomeGestor(String gestor) {
	// try {
	// sigla.setPainelGestor(gestor);
	// LOG.debug("Painel do Gestor inserido");
	// log.append("Painel do Gestor inserido\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao inserir o gestor do Painel");
	// log.append("Erro ao inserir o gestor do painel\n");
	// }
	// }
	//
	// private static void salvarSiglaBancoDeDados() {
	// try {
	// AnaliseCodigoEspanhaDAO siglaDAO = new AnaliseCodigoEspanhaDAO();
	// siglaDAO.salvar(sigla);
	// LOG.debug("Captura inserida no banco");
	// log.append("Captura inserida no banco\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao inserir captura no banco");
	// log.append("Erro ao inserir captura no banco \n");
	// }
	// }
	//
	// private static void capturarVulnerabilidades(WebDriver driver) {
	// try {
	// PageObjectClass page = new PageObjectClass();
	// new WebDriverWait(driver, 10000)
	// .until(ExpectedConditions.presenceOfElementLocated(page.getQuantidadeCriticals()));
	// sigla.setVulnerabilityAlta(
	// Integer.parseInt(driver.findElement(page.getQuantidadeCriticals()).getText().trim()));
	// sigla.setVulnerabilityBaixa(
	// Integer.parseInt(driver.findElement(page.getQuantidadeMinors()).getText().trim()));
	// sigla.setVulnerabilityMedia(
	// Integer.parseInt(driver.findElement(page.getQuantidadeMajors()).getText().trim()));
	// sigla.setVulnerabilityMuitoAlta(
	// Integer.parseInt(driver.findElement(page.getQuantidadeBlockers()).getText().trim()));
	// sigla.setVulnerabilityMuitoBaixa(
	// Integer.parseInt(driver.findElement(page.getQuantidadeInfos()).getText().trim()));
	// LOG.debug(">> Vulnerabilidades capturadas");
	// log.append("Vulnerabilidades capturadas\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao capturar Vulnerabilidades");
	// log.append("Erro ao capturar Vulnerabilidades\n");
	// }
	// }
	//
	// private static void capturarIssues(WebDriver driver) {
	// try {
	// PageObjectClass page = new PageObjectClass();
	// new WebDriverWait(driver, 10000)
	// .until(ExpectedConditions.presenceOfElementLocated(page.getQuantidadeCriticals()));
	// sigla.setIssuesAlta(Integer.parseInt(driver.findElement(page.getQuantidadeCriticals()).getText().trim()));
	// sigla.setIssuesBaixa(Integer.parseInt(driver.findElement(page.getQuantidadeMinors()).getText().trim()));
	// sigla.setIssuesMedia(Integer.parseInt(driver.findElement(page.getQuantidadeMajors()).getText().trim()));
	// sigla.setIssuesMuitoAlta(
	// Integer.parseInt(driver.findElement(page.getQuantidadeBlockers()).getText().trim()));
	// sigla.setIssuesMuitoBaixa(Integer.parseInt(driver.findElement(page.getQuantidadeInfos()).getText().trim()));
	// sigla.setBugs(Integer
	// .parseInt(validaStringComPonto(driver.findElement(page.getQuantidadeBugs()).getText().trim())));
	// sigla.setCodeSmells(Integer.parseInt(
	// validaStringComPonto(driver.findElement(page.getQuantidadeCodeSmells()).getText().trim())));
	// sigla.setVulnerabilidades(Integer.parseInt(
	// validaStringComPonto(driver.findElement(page.getQuantidadeVulnerabilidades()).getText().trim())));
	// LOG.debug(">> Issues capturadas");
	// log.append("Issues capturadas\n");
	// } catch (Exception e) {
	// LOG.debug("Erro ao capturar Issues");
	// log.append("Erro ao capturar Issues\n");
	// }
	// }

}
