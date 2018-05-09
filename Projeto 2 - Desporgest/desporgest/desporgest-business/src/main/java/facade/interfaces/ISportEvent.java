package facade.interfaces;

import java.io.Serializable;
import java.util.List;

import business.Match;
import business.Referee;
import facade.exceptions.ApplicationException;

public interface ISportEvent extends Serializable {

	/**
	 * Returns the id.
	 *
	 * @return
	 */
	public Long getId();
	
	/**
	 * Returns the designation of the sport event.
	 *
	 * @return the designation.
	 */
	public String getDesignation();
	
	/**
	 * Adds a referee to a given match.
	 *
	 * @param m
	 * @param r
	 * @throws ApplicationException
	 *
	 */
	public void addReferee(Match m, Referee r) throws ApplicationException;


	/**
	 * Returns the list of sport event matches that have an incomplete referee
	 * team.
	 *
	 * @return
	 */
	public List<Match> getMatchesWithIncompleteRefereeTeam();

	/**
	 * @param referee
	 * @param match
	 * @return whether the given referee is admissible for a given match 
	 */
	public abstract boolean areCompatible(Referee referee, Match match); 
	
	@Override
	public String toString();
}
