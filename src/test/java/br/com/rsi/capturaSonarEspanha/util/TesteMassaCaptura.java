package br.com.rsi.capturaSonarEspanha.util;

import java.util.ArrayList;

import org.junit.Test;

public class TesteMassaCaptura {
	
	@Test
	public void testeMassaCaptura(){
		ArrayList<MassaCaptura> massa = (ArrayList) LerArquivoCSV.getInformacoesArquivo();
		System.out.println(massa.toString());
	}

}
