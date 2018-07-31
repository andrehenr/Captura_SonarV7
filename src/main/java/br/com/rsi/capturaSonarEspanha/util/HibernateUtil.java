package br.com.rsi.capturaSonarEspanha.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static final SessionFactory fabricaSessoes = criarFabricaDeSessoes();
	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory;
	
	/**
	 * Cria sessoes com o banco de dados
	 * 
	 * @return - Retorna um objeto do tipo SessionFactory
	 * 
	 */
	
	private static SessionFactory criarFabricaDeSessoes(){
		try {
			// Cria a SessionFactory para hibernate.cfg.xml
			Configuration configuration = new Configuration();
			
	        //Informo a localizacao do arquivo de dados do DB e Sonar
	        FileInputStream arquivoDadosProperties = new FileInputStream("./properties/dados.properties");
	        
			//Instancio um Properties
			Properties dadosDB = new Properties();
			
			//Carrego as informações do arquivo de dados do DB e Sonar para o Properties
			dadosDB.load(arquivoDadosProperties);
			
			//Instancio outro Properties porque necessito coletar apenas os dados do DB e passar esse Properties 
			//para o Configuration do hibernate
			Properties properties = new Properties();
			properties.put("hibernate.connection.url", "jdbc:sqlserver://"+dadosDB.getProperty("prop.server.db_host")+";"+"databaseName="+dadosDB.getProperty("prop.server.db_name"));
			properties.put("hibernate.connection.username", dadosDB.getProperty("prop.server.usuarioSQL"));
			properties.put("hibernate.connection.password", dadosDB.getProperty("prop.server.senhaSQL"));
//			//Cria as tabelas do banco de dados "Create|Update|Validade"
//			properties.put("hbm2ddl.auto", "update");
			configuration.addProperties(properties);
			configuration.configure();

			serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			
			return sessionFactory;

		} catch (Throwable ex) {

			System.err.println("Falha ao criar fabrica de sessão." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static void fechaSessao(){
		if(sessionFactory != null){
			sessionFactory.close();
		}
//		StandardServiceRegistryBuilder.destroy(serviceRegistry);
	}
	
	/**
	 * @return - Retorna uma SessionFactory
	 * 
	 */

	public static SessionFactory getFabricasessoes() {
		return fabricaSessoes;
	}
	
	

}