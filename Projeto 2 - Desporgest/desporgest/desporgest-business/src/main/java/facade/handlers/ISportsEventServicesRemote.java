package facade.handlers;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;

import business.Match;
import business.Periodicity;
import business.SportEvent;
import facade.dto.SportEventDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface ISportsEventServicesRemote {

	/**
     * Calls the handler in order to create a sport event.
     * 
     * @param designation
     * @param nParticipants
     * @param nRefereesPerMatch
     * @param beginingDate
     * @param p
     * @throws ApplicationException 
     */
    public void createCupSportEvent(String designation, 
            int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
            Periodicity p) throws ApplicationException;
    
    public void createChampionshipSportEvent(String designation,
            int nParticipants, int nRefereesPerMatch, Calendar beginingDate,
            Periodicity p) throws ApplicationException;
    
    /**
     * Calls the handler to obtain a sport event given a designation.
     * 
     * INCLUDED ONLY FOR VISUALIZATION PURPOSE OF THE STATE IN SIMPLE CLIENT ! 
	 * TO BE REMOVED! 
     * @param sportEventDesignation
     * @return 
     * @throws facade.exceptions.ApplicationException 
     */
    public SportEvent getSportEventByDesignation(String sportEventDesignation) throws ApplicationException;

    
    /**
     * Calls the handler to obtain a sport event given a designation.
     * 
     * @param sportEventDesignation 
     * @throws facade.exceptions.ApplicationException 
     */
    public void selectSportEvent(String sportEventDesignation) throws ApplicationException;
    
    /**
     * Calls the handler to obtain the list of sport event's matches that have 
     * an incomplete referee team.
     * 
     * @return 
     */
    public List<Match> getMatchesCurrentSEWithMissingReferees();
    
    /**
     * Calls the handler to add a referee to a match.
     * 
     * @param matchNumber
     * @param refereeNumber
     * @return 
     * @throws ApplicationException 
     */
    public boolean addReferee(int matchNumber, int refereeNumber) throws ApplicationException;
    
    public Collection<SportEventDTO> getSportEvents() throws ApplicationException;
    
}
