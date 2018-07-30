package br.com.rsi.capturaSonarEspanha.dao;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.rsi.capturaSonarEspanha.util.HibernateUtil;

public class GenericDAO<Entidade> {

	@SuppressWarnings("unused")
	private Class<Entidade> classe;

	// Construtor
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		// API Reflection
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

	}

	/**
	 * Salva um objeto
	 * 
	 * @param entidade - Entidade
	 */
	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricasessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();

		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}

	}

}
