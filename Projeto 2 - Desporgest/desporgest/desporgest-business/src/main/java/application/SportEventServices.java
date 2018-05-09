package application;

import business.Match;
import business.Periodicity;
import business.SportEvent;
import business.handlers.AssignRefereeHandler;
import business.handlers.CreateSportEventHandler;
import facade.dto.SportEventDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.ISportsEventServicesRemote;
import facade.interfaces.ISportEvent;

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * The sport event services class.
 * 
 * @author Thibault Langlois
 */
@WebService 
@Stateless
public class SportEventServices implements ISportsEventServicesRemote{
    
	@EJB
    private CreateSportEventHandler cseh;
	
	@EJB
    private AssignRefereeHandler arh;
    
    /**
     * Calls the handler in order to create a sport event.
     * 
     * @param designation
     * @param nParticipants
     * @param nRefereesPerMatch
     * @param beginingDate
     * @param p
     * @throws facade.exceptions.ApplicationException 
     */
    public void createCupSportEvent(String designation, 
            int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
            Periodicity p) throws ApplicationException {
        cseh.createCupSportEvent(designation, nParticipants, nRefereesPerMatch, beginingDate, p);
    }
    
    public void createChampionshipSportEvent(String designation,
            int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
            Periodicity p) throws ApplicationException {
        cseh.createChampionshipSportEvent(designation, nParticipants, nRefereesPerMatch, beginingDate, p);
    }
    
    /**
     * Calls the handler to obtain a sport event given a designation.
     * 
     * INCLUDED ONLY FOR VISUALIZATION PURPOSE OF THE STATE IN SIMPLE CLIENT ! 
	 * TO BE REMOVED! 
     * @param sportEventDesignation
     * @return 
     * @throws facade.exceptions.ApplicationException 
     */
    public SportEvent getSportEventByDesignation(String sportEventDesignation) throws ApplicationException {
         return cseh.getSportEventByDesignation(sportEventDesignation);
    }

    
    /**
     * Calls the handler to obtain a sport event given a designation.
     * 
     * @param sportEventDesignation 
     * @throws facade.exceptions.ApplicationException 
     */
    public void selectSportEvent(String sportEventDesignation) throws ApplicationException {
         arh.selectSportSportEvent(sportEventDesignation);
    }
    
    /**
     * Calls the handler to obtain the list of sport event's matches that have 
     * an incomplete referee team.
     * 
     * @return 
     */
    public List<Match> getMatchesCurrentSEWithMissingReferees() {
        return arh.getMatchesCurrentSEWithMissingReferees();
    }
    
    /**
     * Calls the handler to add a referee to a match.
     * 
     * @param matchNumber
     * @param refereeNumber
     * @return 
     * @throws facade.exceptions.ApplicationException 
     */
    public boolean addReferee(int matchNumber, int refereeNumber) throws ApplicationException {
        return arh.addReferee(matchNumber, refereeNumber);
    }

	public Collection<SportEventDTO> getSportEvents() throws ApplicationException {
		List<SportEventDTO> result = new LinkedList<>(); 
		for (ISportEvent sportEvent : cseh.getSportEvents())
			result.add(new SportEventDTO(sportEvent.getDesignation(), sportEvent.getId()));
		return result;
	}
}
