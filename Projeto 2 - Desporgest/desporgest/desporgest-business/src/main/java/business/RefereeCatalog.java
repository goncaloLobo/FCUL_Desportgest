package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * The catalog of Referees.
 * 
 * @author Thibault Langlois
 */
@Stateless
public class RefereeCatalog {
   
	/**
	 * Entity manager for accessing the persistence service 
	 */
	@PersistenceContext
	private EntityManager em;
	
    /**
     * Finds a referee given its license number.
     * @param em
     * @param licenceNumber
     * @return 
     */
    public Referee getRefereeByNumber(int licenceNumber) {
         TypedQuery<Referee> q = em.createNamedQuery(Referee.GET_REFEREE_BY_LICENSE_NUMBER,
                Referee.class);
        q.setParameter("number", licenceNumber);
        return q.getSingleResult();
    }
 
}
