package br.com.rsi.capturaSonar.util;

public class MassaCaptura {

	private String idPainel;
	private boolean captura;

	MassaCaptura(String painel, Boolean captura) {
		this.idPainel = painel;
		this.captura = captura;
	}

	public String getNomePainel() {
		return idPainel;
	}

	public boolean getCaptura() {
		return captura;
	}

	@Override
	public String toString() {
		return "MassaCaptura [painel=" + idPainel + "]";
	}

}
