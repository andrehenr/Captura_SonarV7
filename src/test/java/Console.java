import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/appcontext.xml")
public class Console {

	/**
	 * @author thiago.castilho
	 *
	 * Esta classe não faz parte do framework. Utilize esta classe para
	 * realizar experimentos de código.
	 */


	/**
	 * Se for utilizar o framework OpenBDT nesta classe, descomente o bloco abaixo.
	 */
	/*
	 * @Autowired
	 * private WebBrowserScreenElement viewElement;
	 * */

	/**
	 * Se você for executar um teste na web, descomente o exemplo abaixo. Senão,
	 * basta criar seus métodos à vontade, lembrando de anotar com @Test como no outro exemplo abaixo.
	 */
	/*
	 * @Test 
	 * public void setUp(){ System.setProperty("webdriver.chrome.driver", "C:/desenvolvimento/tools/chromedriver/chromedriver.exe");
     * 	viewElement.open("http://www.google.com.br");
	 * 	viewElement.getDriver().manage().window().maximize(); 
	 * }
	 */
	
	/**
	 * Para rodar seus métodos, não se esqueça de inserir a annotation @Test acima de seu método.
	 */
	/*
	 * @Test 
	 * public void testeMetodoExemplo() { List<Integer> inteiros = new ArrayList<Integer>();
	 * 
	 * 	for (int i = 0; i < 10; i++) { 
	 * 		inteiros.add((int)(Math.random()*100)); 
	 * 	}
	 * 
	 * 	for (Integer integer : inteiros) { 
	 * 		System.out.println(">> " + integer.intValue()); 
	 * 	} 
	 * }
	 */

}
