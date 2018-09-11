package br.com.rsi.capturaSonar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LerArquivoCSV {

	public static ArrayList<MassaCaptura> getInformacoesArquivo() {
		
		//Lista de Paineis na massa a serem capturados
		ArrayList<MassaCaptura> listaCaptura = new ArrayList<>();

		// Instancio um Properties
		Properties dadosDB = new Properties();

		// Carrego as informações do arquivo de dados do DB e Sonar para o
		// Properties
		try {
			dadosDB.load(new FileInputStream("./properties/dados.properties"));
		} catch (IOException e1) {
			System.out.println("Erro ao carregar o arquivo dados.properties: " + e1);
		}

		String csvArquivo = dadosDB.getProperty("prop.massa");
		
		try {
			
			// Carrega a planilha
			Workbook workbook = null;
			workbook = Workbook.getWorkbook(new File(csvArquivo));
			// Seleciona a aba do excel
			Sheet sheet = workbook.getSheet(0);
			// Numero de linhas com dados do xls
			int linhas = sheet.getRows();
			if(linhas > 0){
				for (int i = 1; i < linhas; i++) {
					Cell celulaIDPainel = sheet.getCell(0, i); // coluna 0 - ID do Painel
					Cell celulaCaptura = sheet.getCell(1, i); // coluna 1 - true ou false para captura
					System.out.println(celulaIDPainel.getContents().toString()+","+celulaCaptura.getContents().toString());
					//Verifico se o painel foi selecionado para capturar ou não
					boolean captura = false;
					if(celulaCaptura.getContents().toString().equals("true")){
						captura = true;
					}
					//Criando um objeto do tipo massa de captura, que possui o codigo do painel a ser capturado e uma variavel do tipo Boolean que define se o painel deve ou não ser capturado.
					MassaCaptura linhaMassaCaptura = new MassaCaptura(celulaIDPainel.getContents().toString().trim(),captura);
					listaCaptura.add(linhaMassaCaptura);
				}
			}
			
		} catch (NullPointerException e) {
			System.out.println("Catch 01 ---------------------------------");

		} catch (Exception e) {
			System.out.println("Catch 02 ---------------------------------");
		}
		return listaCaptura;
	}

}