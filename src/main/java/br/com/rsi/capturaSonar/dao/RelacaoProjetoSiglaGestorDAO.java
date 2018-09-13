package br.com.rsi.capturaSonar.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rsi.capturaSonar.domain.RelacaoProjetoSiglaGestor;
import br.com.rsi.capturaSonar.util.HibernateUtil;

public class RelacaoProjetoSiglaGestorDAO extends GenericDAO<RelacaoProjetoSiglaGestor> {

	/**
	 * @return - Retorna um objecto RelacaoProjetoSiglaGestorDAO de acordo com o
	 *         Nome do Projeto Pesquisado.
	 * 
	 * @param projeto
	 *            - Nome do projeto a ser pesquisado
	 * 
	 */

	public RelacaoProjetoSiglaGestor buscarGestorESigla(String projeto) {
		Session sessao = HibernateUtil.getFabricasessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(RelacaoProjetoSiglaGestor.class);
			consulta.add(Restrictions.eq("nomeProjeto", projeto));
			consulta.setMaxResults(1);
			RelacaoProjetoSiglaGestor resultado = (RelacaoProjetoSiglaGestor) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
