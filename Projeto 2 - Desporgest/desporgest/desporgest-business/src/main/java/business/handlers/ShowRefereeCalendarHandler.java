package business.handlers;

import business.Match;
import business.Referee;
import business.RefereeCatalog;
import business.SportEvent;
import business.SportEventCatalog;
import facade.dto.MatchDTO;
import facade.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The Show Referee Calendar Handler.
 *
 */
@Stateful
public class ShowRefereeCalendarHandler {

	private Referee currentReferee;
	
	@PersistenceContext
	private EntityManager em;

	@EJB
	private SportEventCatalog sec;

	@EJB
	private RefereeCatalog rc;

	public ShowRefereeCalendarHandler(){
		
	}
	/**
	 * Returns the list of matches assigned to the referee designated by the
	 * license number.
	 *
	 * @param licenseNumber
	 * @return
	 * @throws facade.exceptions.ApplicationException
	 */
	public List<MatchDTO> getRefereeMatches(int licenseNumber) throws ApplicationException {
		List<MatchDTO> ml = new ArrayList<>();

		currentReferee = rc.getRefereeByNumber(licenseNumber);            
		for (Match m : currentReferee.getMatches()) {
			SportEvent se = sec.getSportEventByMatch(em,m);
			ml.add(new MatchDTO(m, se.getDesignation()));
		}
		Collections.sort(ml, MatchDTO.comparator());
		return ml;
	}

	/**
	 * Returns the list of referees assigned to the match that the currentReferee
	 * has in given date
	 *
	 * @param date
	 * @return
	 * @throws facade.exceptions.ApplicationException
	 */
	public List<Referee> getMatchRefereeList(Calendar date) throws ApplicationException {
		List<Referee> rl = new ArrayList<>();
		Match m = findMatch(date);
		if (m == null)
			throw new ApplicationException("Invalid date: no match of the referee in this date.");
		rl.addAll(m.getReferees());
		rl.remove(currentReferee);
		return rl;
	}

	private Match findMatch(Calendar date) throws ApplicationException {
		currentReferee = em.merge(currentReferee);
		for (Match m : currentReferee.getMatches()) {
			if (m.getMatchDate().equals(date)) {
				return m;
			}
		}
		return null;
	}
}
