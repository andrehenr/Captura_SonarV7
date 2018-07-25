Feature: Capturar de Siglas no Sonar

@usrum
Scenario Outline: Para capturar uma sigla no Sonar
  Given estou na pagina inicial do site
  When preencho login
  And preencho senha
  And pressiono botao entrar 
  And inicio a captura dos paineis listados na massa
 
 
 Examples:
 ||
 || 
