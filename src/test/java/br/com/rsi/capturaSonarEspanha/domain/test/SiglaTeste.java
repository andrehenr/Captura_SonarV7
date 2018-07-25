package br.com.rsi.capturaSonarEspanha.domain.test;

import org.junit.Test;

import br.com.rsi.capturaSonarEspanha.dao.SiglaDAO;
import br.com.rsi.capturaSonarEspanha.domain.Sigla;

public class SiglaTeste {
	
	@Test
	public void insereSigla(){
		
		Sigla sigla = new Sigla();
		sigla.setLinhaCodigo(500000);
		sigla.setBugs(1000);
		
		SiglaDAO siglaDAO = new SiglaDAO();
		siglaDAO.salvar(sigla);
		
	}

}
