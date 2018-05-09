package business.handlers;

import business.Participant;
import business.ParticipantCatalog;
import business.Periodicity;
import business.SportEvent;
import business.SportEventCatalog;
import business.utils.DateUtil;
import facade.exceptions.ApplicationException;
import facade.interfaces.ISportEvent;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The sport event handler.
 *
 */
@Stateless
public class CreateSportEventHandler {

	private static final int CUP = 0;
	private static final int CHAMPIONSHIP = 1;

	@EJB private SportEventCatalog seCat;
	@EJB private ParticipantCatalog partCat;
	@PersistenceContext private EntityManager em;

	public CreateSportEventHandler(){
		
	}
	/**
	 * Creates a championship after checking that the provided information is
	 * valid.
	 *
	 * @param designation
	 * @param nParticipants
	 * @param nRefereesPerMatch
	 * @param beginingDate
	 * @param periodicity
	 * @throws ApplicationException
	 *
	 */
	public void createChampionshipSportEvent(String designation,
			int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
			Periodicity periodicity) throws ApplicationException {

		validateChampionshipNParticipants(nParticipants);
		createSportEvent(designation, nParticipants, nRefereesPerMatch, beginingDate,
				periodicity, CHAMPIONSHIP);
	}

	/**
	 * Creates a cup aFter checking that the provided information is valid.
	 *
	 * @param designation
	 * @param nParticipants
	 * @param nRefereesPerMatch
	 * @param beginingDate
	 * @param periodicity
	 * @throws ApplicationException
	 *
	 */
	public void createCupSportEvent(String designation,
			int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
			Periodicity periodicity) throws ApplicationException {

		validateCupNParticipants(nParticipants);
		createSportEvent(designation, nParticipants, nRefereesPerMatch, beginingDate,
				periodicity, CUP);
	}

	/**
	 * Takes care of the common part of the creation of a sport event
	 *
	 * @param designation
	 * @param nParticipants
	 * @param nRefereesPerMatch
	 * @param beginingDate
	 * @param p
	 * @throws ApplicationException
	 *
	 */
	private void createSportEvent(String designation,
			int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
			Periodicity periodicity, int evenType) throws ApplicationException {

		validateBeginingDate(beginingDate);

		List<SportEvent> sel = seCat.findSportEventByDesignation(em, designation);
		if (sel.isEmpty()) {
			List<Participant> participants = partCat.makeParticipants(nParticipants);
			SportEvent se;
			if (evenType == CUP) 
				se = seCat.makeCup(designation, participants, nRefereesPerMatch, beginingDate, periodicity); 
			else
				se = seCat.makeChampionship(designation, participants, nRefereesPerMatch, beginingDate, periodicity);
			
			em.persist(se);

		}
	}

	/**
	 * Validates the number of participants.
	 *
	 * @param nParticipants
	 * @param type
	 * @throws ApplicationException
	 */
	private void validateCupNParticipants(int nParticipants) throws ApplicationException {
		if (nParticipants <= 0) {
			throw new ApplicationException("The number of participants must be greater greater than 0.");
		}
		if (isNotPowerOf2(nParticipants)) {
			throw new ApplicationException("The number of participants must be a power of 2.");
		}
	}

	private void validateChampionshipNParticipants(int nParticipants) throws ApplicationException {
		if (nParticipants <= 0) {
			throw new ApplicationException("The number of participants must be greater than 0.");
		}
		if (nParticipants % 2 != 0) {
			throw new ApplicationException("The number of participants must be even.");
		}
	}

	/**
	 * Is n a power of 2 ?
	 *
	 * @param n
	 * @return
	 */
	private boolean isNotPowerOf2(int n) {
		return ((n & (n - 1)) != 0);
	}

	/**
	 * Checks if date is valid: saturday or sunday.
	 *
	 * @param beginingDate
	 * @throws ApplicationException
	 */
	private void validateBeginingDate(Calendar beginingDate) throws ApplicationException {
		if (!DateUtil.isSaturdayOrSunday(beginingDate)) {
			throw new ApplicationException("The sport event should start a saturday or a sunday.");
		}
	}

	/**
	 * Finds a sport event based on a designation.
	 *
	 *
	 * INCLUDED ONLY FOR VISUALIZATION PURPOSE OF THE STATE IN SIMPLE CLIENT !
	 * TO BE REMOVED!
	 *
	 * @param designation
	 * @return
	 * @throws facade.exceptions.ApplicationException
	 */
	public SportEvent getSportEventByDesignation(String designation) throws ApplicationException {
		SportEvent se;
		se = seCat.getSportEventByDesignation(em, designation);
		return se;
	}
	
	public List<ISportEvent> getSportEvents() throws ApplicationException {
		return new LinkedList<>(seCat.getSportEvents(em));
	}

}
