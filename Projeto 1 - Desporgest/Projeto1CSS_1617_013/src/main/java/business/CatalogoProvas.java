package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

/**
 * Catalogo de provas
 * 
 * @author css013
 *
 */
public class CatalogoProvas {

	private EntityManager em;

	/**
	 * Constroi um catalogo de provas dado um entity manager
	 */
	public CatalogoProvas(EntityManager em) {
		this.em = em;
	}

	/**
	 * Encontra uma prova dada a sua designacao
	 * @param designacao Designacao da prova
	 * @return Prova com a designacao pretendida
	 * @throws ApplicationException Caso nao exista nenhuma prova com essa designacao
	 */
	public Prova getProva(String designacao) throws ApplicationException {
		TypedQuery<Prova> query = em.createNamedQuery(Prova.FIND_BY_DESIGNATION, Prova.class);
		query.setParameter(Prova.DESIGNACAO, designacao);
		try {
			if(query.getSingleResult() != null)
				return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("A prova nao foi encontrada", e);
		}
		return null;
	}

	/**
	 * Encontra as provas com a designacao
	 * @param designacao Designacao da prova
	 * @return Provas com a designacao pretendida
	 * @throws ApplicationException Caso nao existe nenhuma prova com essa designacao
	 */
	public List<Prova> getProvas(String designacao) throws ApplicationException {
		TypedQuery<Prova> query = em.createNamedQuery(Prova.FIND_BY_DESIGNATION, Prova.class);
		query.setParameter(Prova.DESIGNACAO, designacao);
		try {
			if (query.getMaxResults() > 0){
				return query.getResultList();
			} else{
				return new ArrayList<Prova>();
			}
		} catch (PersistenceException e) {
			throw new ApplicationException("A prova com essa designacao nao foi encontrada", e);
		}
	}

	/**
	 * Adiciona uma nova prova
	 * 
	 * @param tipo
	 *            tipo da prova
	 * @param designacao
	 *            designacao da prova
	 * @param dataInicio
	 *            data de inicio da prova
	 * @param numParticipantes
	 *            numero de participantes da prova
	 * @param numArbitrosEncontro
	 *            numero de arbitros por encontro da prova
	 * @param periocidade
	 *            periocidade da prova
	 */
	public Prova adicionaProva(String tipo, String designacao, Date dataInicio, int numParticipantes,
			int numArbitrosEncontro, Periodicidade periocidade) {
		Prova p = new Prova(tipo, designacao, dataInicio, numParticipantes, numArbitrosEncontro, periocidade);
		em.persist(p);
		return p;
	}
}
