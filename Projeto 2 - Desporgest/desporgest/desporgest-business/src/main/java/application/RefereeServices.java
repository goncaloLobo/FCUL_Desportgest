package application;

import business.Referee;
import business.handlers.ShowRefereeCalendarHandler;
import facade.dto.MatchDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IRefereeServicesRemote;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;


/**
 * The referee services class.
 * 
 * @author Thibault Langlois
 */
@WebService
@Stateless
public class RefereeServices implements IRefereeServicesRemote{

	@EJB
	private ShowRefereeCalendarHandler srch;

	/**
	 * Obtains a referee's calendar given his license number.
	 * This referee becomes the current referee
	 * @param licenseNumber
	 * @return 
	 */
	public List<MatchDTO> getRefereeCalendar(int licenseNumber) throws ApplicationException {
		return srch.getRefereeMatches(licenseNumber);
	}

	/**
	 * Obtains the referees of the match that the current referee
	 * has in the given date
	 * @param date
	 * @return 
	 * @throws ApplicationException 
	 */
	public List<Referee> getMatchRefereeList(Calendar date) throws ApplicationException {
		return srch.getMatchRefereeList(date);
	}
}
