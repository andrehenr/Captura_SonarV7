package br.com.rsi.capturaSonarEspanha.util;

public class MassaCaptura {

	private String nomePainel;

	MassaCaptura(String painel) {
		this.nomePainel = painel;
	}

	public String getPainel() {
		return nomePainel;
	}


	@Override
	public String toString() {
		return "MassaCaptura [painel=" + nomePainel + "]";
	}
	
	

}
