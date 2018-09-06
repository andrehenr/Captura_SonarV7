package br.com.rsi.capturaSonarEspanha.util;

public class MassaCaptura {

	private String nomePainel;
	private Boolean captura;

	MassaCaptura(String painel, boolean captura) {
		this.nomePainel = painel;
		this.captura = captura;
	}

	public String getPainel() {
		return nomePainel;
	}

	public boolean isCaptura() {
		return captura;
	}

	@Override
	public String toString() {
		return "MassaCaptura [painel=" + nomePainel + "]";
	}

}
