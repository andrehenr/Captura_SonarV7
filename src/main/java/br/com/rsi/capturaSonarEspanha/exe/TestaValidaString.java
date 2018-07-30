package br.com.rsi.capturaSonarEspanha.exe;

public class TestaValidaString {
	
	public static void main(String[] args) {
		String comPonto = "17.896";
		String comPontoEK = "17.8k";
		String semPontoSemK = "17896";
		
		
		System.out.println("Resultado semPontoSemK");
		System.out.println("Antes "+semPontoSemK);
		System.out.println("Depois "+Executora.validaStringComPonto(semPontoSemK));
		System.out.println("______________________");
		
		System.out.println("Resultado comPonto");
		System.out.println("Antes "+comPonto);
		System.out.println("Depois "+Executora.validaStringComPonto(comPonto));
		System.out.println("______________________");
		
		System.out.println("Resultado semPontoSemK");
		System.out.println("Antes "+comPontoEK);
		System.out.println("Depois "+Executora.validaStringComPonto(comPontoEK));
		System.out.println("______________________");
		
	}
}
