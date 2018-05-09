package facade.handlers;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Remote;

import business.Referee;
import facade.dto.MatchDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IRefereeServicesRemote {
    
    /**
     * Obtains a referee's calendar given his license number.
     * This referee becomes the current referee
     * @param licenseNumber
     * @return 
     * @throws facade.exceptions.ApplicationException
     */
    public List<MatchDTO> getRefereeCalendar(int licenseNumber) throws ApplicationException;
    
    /**
     * Obtains the referees of the match that the current referee
     * has in the given date
     * @param date
     * @return 
     * @throws facade.exceptions.ApplicationException 
     */
    public List<Referee> getMatchRefereeList(Calendar date) throws ApplicationException;
	
}
