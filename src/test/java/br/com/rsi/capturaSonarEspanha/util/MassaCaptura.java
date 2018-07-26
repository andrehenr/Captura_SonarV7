package br.com.rsi.capturaSonarEspanha.util;

public class MassaCaptura {

	private String nome;
	private String gestor;

	MassaCaptura(String gestor, String painel) {
		this.gestor = gestor;
		this.nome = painel;
	}

	public String getPainel() {
		return nome;
	}

	public String getGestor() {
		return gestor;
	}

	@Override
	public String toString() {
		return "MassaCaptura [painel=" + nome + ", gestor=" + gestor + "]";
	}
	
	

}
