Feature: Capturar de Siglas no Sonar

@usrum
Scenario Outline: Para capturar uma sigla no Sonar
  Given estou na pagina inicial do site
  When preencho login
  And preencho senha
  And pressiono botao entrar 
  And capturo os paineis listados na massa
  And entro na Issues
  And entro em vulnerability
  And entro em severity
 
 
 Examples:
 ||
 || 
