package business;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.exceptions.ApplicationException;

/**
 * Match catalog.
 *
 */
@Stateless
public class MatchCatalog {

	@PersistenceContext
	private EntityManager em;
    /**
     * Returns a Match given its unique number.
     *
     * @param em
     * @param number
     * @return a match instance.
     */
    public Match getMatchByNumber(int number) throws ApplicationException {
        Match m = em.find(Match.class, number);
        if (m == null) {
            throw new ApplicationException("Match with number" + number + "not found.");
        }
        return m;
    }

}
