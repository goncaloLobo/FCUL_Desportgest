package business.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import business.Match;
import business.MatchCatalog;
import business.Referee;
import business.RefereeCatalog;
import business.SportEvent;
import business.SportEventCatalog;
import facade.exceptions.ApplicationException;

/**
 *
 */
@Stateful
public class AssignRefereeHandler {

	private SportEvent currentSportEvent;
	
	@PersistenceContext
	private EntityManager em;

	@EJB
	private RefereeCatalog reCatalog;

	@EJB
	private MatchCatalog maCatalog;
	
	@EJB
	private SportEventCatalog seCat;
	
	public AssignRefereeHandler(){
		
	}
	/**
	 * Selects the sport event given its designation
	 *
	 * @param sportEventDesignation
	 * @throws ApplicationException
	 */
	public void selectSportSportEvent(String sportEventDesignation) throws ApplicationException {
		currentSportEvent = seCat.getSportEventByDesignation(em, sportEventDesignation);
	}

	/**
	 * Returns the list of the matches of the current sport event that have an
	 * incomplete referee team.
	 *
	 * @return
	 */
	public List<Match> getMatchesCurrentSEWithMissingReferees() {
		return new ArrayList<>(currentSportEvent.getMatchesWithIncompleteRefereeTeam());
	}

	/**
	 * Adds a referee (given its license number) to a match (represented by its
	 * number) provided the rules are
	 *
	 * @param matchNumber
	 * @param licenseNumber
	 * @return
	 * @throws ApplicationException
	 */
	public boolean addReferee(int matchNumber, int licenseNumber) throws ApplicationException {
		boolean result = false;
		Referee referee = reCatalog.getRefereeByNumber(licenseNumber);
		Match match = maCatalog.getMatchByNumber(matchNumber);
		currentSportEvent = em.merge(currentSportEvent);
		result = referee.isFree(match.getMatchDate()) && currentSportEvent.areCompatible(referee, match);
		if (result) {
			currentSportEvent.addReferee(match, referee);
		}
		return result;
	}

}
