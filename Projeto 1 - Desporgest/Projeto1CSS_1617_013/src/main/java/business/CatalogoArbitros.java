package business;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

/**
 * Catalogo de arbitros
 * 
 * @author css013
 *
 */
public class CatalogoArbitros {

	private EntityManager em;

	/**
	 * Constroi um catalogo de arbitros dado um entity manager
	 */
	public CatalogoArbitros(EntityManager em) {
		this.em = em;

	}

	/**
	 * Encontra um arbitro dado o seu numero federativo
	 * 
	 * @param numFederativo Numero Federativo do arbitro
	 * @return O objeto arbitro correspondente a esse numero federativo
	 * @throws ApplicationException Caso nao seja encontrado um arbitro com esse numero federativo
	 */
	public Arbitro getArbitro(int numFederativo) throws ApplicationException {
		TypedQuery<Arbitro> query = em.createNamedQuery(Arbitro.FIND_BY_NUM_FEDERATIVO, Arbitro.class);
		query.setParameter(Arbitro.NUM_FEDERATIVO, numFederativo);
		try {
			if(query.getSingleResult() != null)
				return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Arbitro not found ", e);
		}
		return null;
	}

}
