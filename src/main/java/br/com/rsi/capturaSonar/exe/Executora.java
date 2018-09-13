package br.com.rsi.capturaSonar.exe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.rsi.capturaSonar.dao.AnaliseCodigoDAO;
import br.com.rsi.capturaSonar.dao.RelacaoProjetoSiglaGestorDAO;
import br.com.rsi.capturaSonar.domain.AnaliseCodigo;
import br.com.rsi.capturaSonar.domain.RelacaoProjetoSiglaGestor;
import br.com.rsi.capturaSonar.pages.PageObjectClass;
import br.com.rsi.capturaSonar.util.LerArquivoCSV;
import br.com.rsi.capturaSonar.util.MassaCaptura;
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

			URL_SONAR = dadosDB.getProperty("prop.server.host");

			open(driver, URL_SONAR + "/sessions/new");
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
			AnaliseCodigoDAO.fecharConexao();
		}

	}
	// Termino da metodo main

	private static String URL_SONAR;

	private static Logger LOG = Logger.getLogger(Executora.class);

	private static AnaliseCodigo sigla; // Objeto utilizado para a
											// persistencia
											// de cada
	// painel
	// da massa de captura

	private static FileWriter arq;
	private static StringBuilder log;
	private static PrintWriter gravarArq;

	private static String nomePainel;

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
					if (massaCapturaCompleta.get(i).getCaptura()) {
						sigla = new AnaliseCodigo();
						nomePainel = massaCapturaCompleta.get(i).getNomePainel();
						log.append("___________________________________\n");
						selecionaPainel(URL_SONAR + "/dashboard?id=" + nomePainel, driver);
					}

				} catch (Exception e) {
					// Se ocorrer algum erro na captura de um painel eu passo
					// para a proxima linha
					LOG.debug("Erro ao capturar: " + massaCapturaCompleta.get(i).getNomePainel());
					log.append("Erro ao capturar: " + massaCapturaCompleta.get(i).getNomePainel() + "\n");

				} finally {
					i++;
				}
			}

			escreverLog();

		} catch (Exception e) {
			LOG.debug("Erro ao capturar siglas");
			log.append("Erro ao capturar siglas \n");
		}

	}

	private static void selecionaPainel(String painel, WebDriver driver) {
		try {
			LOG.debug("Iniciando captura em: " + painel);
			log.append("Iniciando captura em: " + painel + "\n");
			sigla.setUrl(painel);
			driver.navigate().to(painel);
			Thread.sleep(7000);
			if (driver.getPageSource().toString()
					.contains("Either it has never been analyzed successfully or it has been deleted.")) {
				throw new Exception();
			}
			capturaCobertura(driver);
		} catch (Exception e) {
			LOG.debug("Erro ao acessar o painel: " + painel);
			log.append("Erro ao acessar o painel: " + painel + "\n");
		}

	}

	private static void capturaCobertura(WebDriver driver) {
		String cobertura = "0.0%";
		if (driver.getPageSource().toString().contains("Cobertura")) {
			By elementoExibeCobertura = new PageObjectClass().getLabelCobertura();
			new WebDriverWait(driver, 5000).until(ExpectedConditions.presenceOfElementLocated(elementoExibeCobertura));
			cobertura = driver.findElement(elementoExibeCobertura).getText().trim().toString();
		}
		sigla.setCobertura(cobertura);
		LOG.debug("Cobertura Capturada");
		log.append("Cobertura Capturada \n");
		capturaNomePainel(driver);
	}

	private static void capturaNomePainel(WebDriver driver) {
		By nomePainel = new PageObjectClass().getLabelNomePainel();
		String nomeProjeto = driver.findElement(nomePainel).getAttribute("title").toString();
		sigla.setNomeProjeto(nomeProjeto);
		LOG.debug("Nome do Painel Capturado");
		log.append("Nome do Painel Capturado \n");
		capturaQualityGate(driver);
	}

	private static void capturaQualityGate(WebDriver driver) {
		By qualityGate = new PageObjectClass().getLabelQualityGate();
		String resultadoQualityGate = driver.findElement(qualityGate).getText().toString().trim();
		sigla.setQualidade(resultadoQualityGate);
		LOG.debug("Quality Gate Capturado");
		log.append("Quality Gate Capturado \n");
		capturaDataAnalise(driver);
	}

	private static void capturaDataAnalise(WebDriver driver) {
		// Capturando a data de execução no Sonar e separando o dia, mes e ano
		By dataExecucao = new PageObjectClass().getLabelDataExecucao();
		String[] dataExecucaoAtual = driver.findElement(dataExecucao).getText().toString().split(" de ");
		Calendar data = Calendar.getInstance();
		data.set(Integer.parseInt(dataExecucaoAtual[2].substring(0, 4)), mesComoInteiro(dataExecucaoAtual[1]),
				Integer.parseInt(dataExecucaoAtual[0]), 0, 0, 0);
		Date dataExecucaoFormatoDate = new Date(data.getTime().getTime());
		sigla.setDataSonar(dataExecucaoFormatoDate);
		LOG.debug("Data de Execução Capturada");
		log.append("Data de Execução Capturada \n");
		capturaVersao(driver);
	}

	private static void capturaVersao(WebDriver driver) {
		String versao = driver.findElement(new PageObjectClass().getLabelVersao()).getText().toString().trim()
				.split(" ")[1];
		sigla.setVersao(versao);
		LOG.debug("Versao Capturada");
		log.append("Versao Capturada \n");
		capturaIssues(driver);
	}

	private static void capturaIssues(WebDriver driver) {
		PageObjectClass page = new PageObjectClass();
		// Captura do bugs
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false&types=BUG");
		sigla.setBugs(verificaIssues(page.getQtdBugs(), driver));
		LOG.debug("Bugs Capturados");
		log.append("Bugs Capturados \n");

		// Captura dos code smells
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false&types=CODE_SMELL");
		sigla.setCodeSmall(verificaIssues(page.getQtdCodeSmell(), driver));
		LOG.debug("CodeSmells Capturados");
		log.append("CodeSmells Capturados \n");

		// Captura do total de vulnerabilidades
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false&types=VULNERABILITY");
		sigla.setVulnerabilidades(verificaIssues(page.getQtdVulnerabilidade(), driver));
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false&types=VULNERABILITY");
		capturaVulnerabilidadesporGravidade(driver);
		LOG.debug("Vulnerabilidades Capturadas");
		log.append("Vulnerabilidades Capturadas \n");

		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false");
		capturaIssuesPorGravidade(driver);
	}

	private static void capturaVulnerabilidadesporGravidade(WebDriver driver) {
		PageObjectClass page = new PageObjectClass();
		new WebDriverWait(driver, 10000)
				.until(ExpectedConditions.presenceOfElementLocated(page.getLabelVulnerabilidadeBlocker()));
		sigla.setVulnerabilityMuitoAlta(
				validarQuantidadeVulnerabilidades(page.getLabelVulnerabilidadeBlocker(), driver));
		sigla.setVulnerabilityAlta(validarQuantidadeVulnerabilidades(page.getLabelVulnerabilidadeCritical(), driver));
		sigla.setVulnerabilityMedia(validarQuantidadeVulnerabilidades(page.getLabelVulnerabilidadeMajor(), driver));
		sigla.setVulnerabilityBaixa(validarQuantidadeVulnerabilidades(page.getLabelVulnerabilidadeMinor(), driver));
		sigla.setVulnerabilityMuitoBaixa(validarQuantidadeVulnerabilidades(page.getLabelVulnerabilidadeInfo(), driver));
	}

	private static void capturaIssuesPorGravidade(WebDriver driver) {
		try {
			PageObjectClass page = new PageObjectClass();
			new WebDriverWait(driver, 10000).until(ExpectedConditions.presenceOfElementLocated(page.getLabelBlocker()));
			sigla.setIssuesMuitoAlta(validarQuantidadeIssues(page.getLabelBlocker(), driver));
			sigla.setIssuesAlta(validarQuantidadeIssues(page.getLabelCritical(), driver));
			sigla.setIssuesMedia(validarQuantidadeIssues(page.getLabelMajor(), driver));
			sigla.setIssuesBaixa(validarQuantidadeIssues(page.getLabelMinor(), driver));
			sigla.setIssuesMuitoBaixa(validarQuantidadeIssues(page.getLabelInfo(), driver));
			LOG.debug("Issues Capturadas");
			log.append("Issues Capturadas \n");
			capturarEsforcoConfiabilidade(driver);
		} catch (Exception e) {
			LOG.debug("Erro ao capturar issues");
			log.append("Erro ao capturar issues\n");
		}

	}

	private static int validarQuantidadeIssues(By labelIssue, WebDriver driver) {
		PageObjectClass page = new PageObjectClass();
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false");
		new WebDriverWait(driver, 10000).until(ExpectedConditions.presenceOfElementLocated(labelIssue));
		String valorElemento = driver.findElement(labelIssue).getText().toString();
		int valorFinal = 0;
		if (valorElemento.contains("k")) {
			driver.findElement(labelIssue).click();
			new WebDriverWait(driver, 10000)
					.until(ExpectedConditions.presenceOfElementLocated(page.getTotalProblemasComVirgula()));
			String[] problemasSeparados = driver.findElement(page.getTotalProblemasComVirgula()).getText().toString()
					.split("\\/");
			valorFinal = Integer.parseInt(problemasSeparados[1].replace('.', ' ').replaceAll(" ", ""));

		} else {
			if (valorElemento.contains("Informativo")) {
				valorFinal = Integer.parseInt(valorElemento.substring(valorElemento.lastIndexOf('o') + 1).trim());
			} else {
				valorFinal = Integer.parseInt(valorElemento);
			}
		}
		return valorFinal;
	}

	private static int validarQuantidadeVulnerabilidades(By labelIssue, WebDriver driver) {
		PageObjectClass page = new PageObjectClass();
		driver.navigate().to(URL_SONAR + "/project/issues?id=" + nomePainel + "&resolved=false&types=VULNERABILITY");
		new WebDriverWait(driver, 10000).until(ExpectedConditions.presenceOfElementLocated(labelIssue));
		String valorElemento = driver.findElement(labelIssue).getText().toString();
		int valorFinal = 0;
		if (valorElemento.contains("k")) {
			driver.findElement(labelIssue).click();
			new WebDriverWait(driver, 10000)
					.until(ExpectedConditions.presenceOfElementLocated(page.getTotalProblemasComVirgula()));
			String[] problemasSeparados = driver.findElement(page.getTotalProblemasComVirgula()).getText().toString()
					.split("\\/");
			valorFinal = Integer.parseInt(problemasSeparados[1].replace(',', ' ').replaceAll(" ", ""));

		} else {
			valorFinal = Integer.parseInt(valorElemento);
		}
		return valorFinal;
	}

	// private static void capturaDebitoTecnico(WebDriver driver) {
	// PageObjectClass page = new PageObjectClass();
	// driver.navigate().to(URL_SONAR + "/component_measures?id=" + nomePainel);
	// new WebDriverWait(driver, 10000)
	// .until(ExpectedConditions.presenceOfElementLocated(page.getModoExibicaoPorEsforco()));
	// driver.findElement(page.getModoExibicaoPorEsforco()).click();
	// new WebDriverWait(driver, 10000)
	// .until(ExpectedConditions.presenceOfElementLocated(page.getDebitoTecnico()));
	// sigla.setDebitoTecnico(driver.findElement(page.getDebitoTecnico()).getText().toString().trim());
	// capturarEsforcoConfiabilidade(driver);
	// }

	private static void capturarEsforcoConfiabilidade(WebDriver driver) {
		try {
			PageObjectClass page = new PageObjectClass();
			driver.navigate().to(URL_SONAR + "/component_measures?id=" + nomePainel);
			new WebDriverWait(driver, 10000)
					.until(ExpectedConditions.presenceOfElementLocated(page.getLinkConfiablidade()));
			driver.findElement(page.getLinkConfiablidade()).click();
			sigla.setEsforcoConfiabilidade(
					driver.findElement(page.getRemediationEffortConfiabilidade()).getText().toString().trim());
			LOG.debug("Esforço de conformidade capturado com sucesso");
			log.append("Esforço de conformidade capturado com sucesso\n");
		} catch (Exception e) {
			LOG.debug("Erro ao capturar o esforço de conformidade");
			log.append("Erro ao capturar o esforço de conformidade\n");
		}
		capturarEsforcoSeguranca(driver);
	}

	private static void capturarEsforcoSeguranca(WebDriver driver) {
		try {
			PageObjectClass page = new PageObjectClass();
			driver.navigate().to(URL_SONAR + "/component_measures?id=" + nomePainel);
			new WebDriverWait(driver, 10000)
					.until(ExpectedConditions.presenceOfElementLocated(page.getLinkSeguranca()));
			driver.findElement(page.getLinkSeguranca()).click();
			sigla.setEsforcoSeguranca(
					driver.findElement(page.getRemediationEffortSeguranca()).getText().toString().trim());
			LOG.debug("Esforço de segurança capturado com sucesso");
			log.append("Esforço de segurança capturado com sucesso\n");
		} catch (Exception e) {
			LOG.debug("Erro ao capturar o esforço de segurança");
			log.append("Erro ao capturar o esforço de segurança\n");
		}
		capturarLinhasCodigo(driver);
	}

	private static void capturarLinhasCodigo(WebDriver driver) {
		try {
			PageObjectClass page = new PageObjectClass();
			driver.navigate().to(URL_SONAR + "/component_measures?id=" + nomePainel);
			new WebDriverWait(driver, 10000).until(ExpectedConditions.presenceOfElementLocated(page.getLinkTamanho()));
			driver.findElement(page.getLinkTamanho()).click();
			sigla.setLinhaCodigo(validarLinhasCodigo(page.getQtdLinhasCodigo(), driver));
			LOG.debug("Linhas de código capturadas com sucesso");
			log.append("Linhas de código capturadas com sucesso\n");
		} catch (Exception e) {
			LOG.debug("Erro ao capturar linhas de código");
			log.append("Erro ao capturar linhas de código\n");
		}
		inserirDescricao();
	}

	private static int validarLinhasCodigo(By qtdLinhasCodigo, WebDriver driver) {
		int linhas = 0;
		String linhasCodigo = driver.findElement(qtdLinhasCodigo).getText().toString().trim();
		if (linhasCodigo.contains(".")) {
			linhas = Integer.parseInt(linhasCodigo.replace('.', ' ').replaceAll(" ", ""));
		} else {
			linhas = Integer.parseInt(linhasCodigo);
		}
		return linhas;
	}

	// Metodo para capturar o total de bugs e o total de vulnerabilidades
	private static int verificaIssues(By quantidadePaginaProblemas, WebDriver driver) {
		PageObjectClass page = new PageObjectClass();
		new WebDriverWait(driver, 2000).until(ExpectedConditions.presenceOfElementLocated(quantidadePaginaProblemas));
		String valorElemento = driver.findElement(quantidadePaginaProblemas).getText().toString();
		int valorFinal = 0;
		if (valorElemento.contains("k")) {
			String[] problemasSeparados = driver.findElement(page.getTotalProblemasComVirgula()).getText().toString()
					.split("\\/");
			valorFinal += Integer.parseInt(problemasSeparados[1].replace('.', ' ').replaceAll(" ", ""));

		} else {
			valorFinal = Integer.parseInt(valorElemento);
		}
		return valorFinal;
	}

	private static int mesComoInteiro(String mesComoString) {

		Map<String, Integer> meses = new HashMap<String, Integer>();

		meses.put("janeiro", 1);
		meses.put("fevereiro", 2);
		meses.put("março", 3);
		meses.put("abril", 4);
		meses.put("maio", 5);
		meses.put("junho", 6);
		meses.put("julho", 7);
		meses.put("agosto", 8);
		meses.put("setembro", 9);
		meses.put("outubro", 10);
		meses.put("novembro", 11);
		meses.put("dezembro", 12);

		return meses.get(mesComoString) - 1;
	}

	private static void inserirDescricao() {
		try {
			Properties arquivo = getProperties();
			sigla.setDescricao(arquivo.getProperty("prop.server.descricao"));
			LOG.debug("Descricao inserida");
			log.append("Descricao inserida\n");
			inserirDataCaptura();

		} catch (Exception e) {
			LOG.debug("Erro ao inserir descricao");
			log.append("Erro ao inserir descricao\n");
		}
	}

	private static void inserirDataCaptura() {
		try {
			Date dataAtual = Calendar.getInstance().getTime();
			sigla.setDataCaptura(dataAtual);
			LOG.debug("Data de Captura inserida");
			log.append("Data de Captura inserida\n");
			relacionarProjetoSiglaGestor();

		} catch (Exception e) {
			LOG.debug("Erro ao inserir data de Captura");
			log.append("Erro ao inserir data de Captura \n");
		}
	}

	private static void relacionarProjetoSiglaGestor() {
		try {
			RelacaoProjetoSiglaGestorDAO relacaoDAO = new RelacaoProjetoSiglaGestorDAO();
			RelacaoProjetoSiglaGestor relacao = relacaoDAO.buscarGestorESigla(sigla.getNomeProjeto());
			sigla.setPainelGestor(relacao.getPainelGestor());
			sigla.setSigla(relacao.getSigla());
			LOG.debug("Relacionamento entre projeto e gestor criado com sucesso");
			log.append("Relacionamento entre projeto e gestor criado com sucesso\n");
			salvarSiglaBancoDeDados();
		} catch (Exception e) {
			LOG.debug("Erro ao criar relacionamento entre projeto e gestor");
			log.append("Erro ao criar relacionamento entre projeto e gestor\n");
		}
	}

	private static void salvarSiglaBancoDeDados() {
		try {
			AnaliseCodigoDAO siglaDAO = new AnaliseCodigoDAO();
			siglaDAO.salvar(sigla);
			LOG.debug("Captura inserida no banco");
			log.append("Captura inserida no banco\n");
			System.out.println(sigla);
		} catch (Exception e) {
			LOG.debug("Erro ao inserir captura no banco");
			log.append("Erro ao inserir captura no banco \n");
		}
	}

}
