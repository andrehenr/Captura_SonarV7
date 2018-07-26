package br.com.rsi.capturaSonarEspanha.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class LerArquivoCSV {

	public static List<MassaCaptura> getInformacoesArquivo() {
		
		
		//Instancio um Properties
		Properties dadosDB = new Properties();
		
		//Carrego as informações do arquivo de dados do DB e Sonar para o Properties
		try {
			dadosDB.load(new FileInputStream("./properties/dados.properties"));
		} catch (IOException e1) {
			System.out.println("Erro ao carregar o arquivo dados.properties: "+e1);
		}
		
		String csvArquivo = dadosDB.getProperty("prop.massa");
		BufferedReader conteudoCSV = null;
		String linha = "";
		String csvSeparadorCampo = ",";
		String[] sigla = null;
		List<MassaCaptura> massaCapturaCompleta = new ArrayList<>();
		try {
			conteudoCSV = new BufferedReader(new FileReader(csvArquivo));
			while ((linha = conteudoCSV.readLine()) != null) {
				sigla = linha.split(csvSeparadorCampo);
				MassaCaptura linhaDaMassa = new MassaCaptura(sigla[0], sigla[1]);
				massaCapturaCompleta.add(linhaDaMassa);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado: \n" + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBounds: \n" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Erro: \n" + e.getMessage());
		} finally {
			if (conteudoCSV != null) {
				try {
					conteudoCSV.close();
				} catch (IOException e) {
					System.out.println("IO Erro: \n" + e.getMessage());
				}
			}
		}
		return massaCapturaCompleta;

	}

}