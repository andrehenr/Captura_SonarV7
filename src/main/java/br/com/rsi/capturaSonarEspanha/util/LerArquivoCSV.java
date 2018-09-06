package br.com.rsi.capturaSonarEspanha.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LerArquivoCSV {

	public static List<MassaCaptura> getInformacoesArquivo() {

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
			for (int i = 1; i < linhas; i++) {
				
				Cell celulaIDPainel = sheet.getCell(1, i); // coluna 1 - ID do Painel
				Cell celulaCaptura = sheet.getCell(2, i); // coluna 2 - true ou false para captura
				MassaCaptura linhaMassaCaptura = new MassaCaptura(celulaIDPainel.getContents().toString().trim(),Boolean.getBoolean(celulaCaptura.getContents().toString()));
				
			}
		} catch (NullPointerException e) {
			System.out.println("Catch 01 ---------------------------------");

		} catch (Exception e) {
			System.out.println("Catch 02 ---------------------------------");
		}
	}
		
		
	}

}