package presentation.web.model;

import java.util.LinkedList;

import facade.exceptions.ApplicationException;
import facade.handlers.IRefereeServicesRemote;
import facade.handlers.ISportsEventServicesRemote;
import facade.interfaces.IMatch;

/**
 * Helper class to assist in the response of associar arbitro.
 * This class is the response information expert.
 * 
 */
public class NewAssignRefereeModel extends Model {

	private String designation;
	private String number;
	private String numeroArbitro;
	private ISportsEventServicesRemote addSportsEventServicesHandler;
	private IRefereeServicesRemote addRefereeServicesHandler;
	public Iterable<? extends IMatch> local;
	
	/**
	 * 
	 * @param addSportsEventServicesHandler
	 */
	public void setShowMatches(ISportsEventServicesRemote addSportsEventServicesHandler) {
		this.addSportsEventServicesHandler = addSportsEventServicesHandler;
	}
	
	/**
	 * 
	 * @param addRefereeServicesHandler
	 */
	public void setNewAssignRefereeHandler(IRefereeServicesRemote addRefereeServicesHandler){
		this.addRefereeServicesHandler = addRefereeServicesHandler;
	}
	
	/**
	 * 
	 * @param designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;	
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDesignation() {
		return designation;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * 
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNumeroArbitro() {
		return numeroArbitro;
	}
	
	/**
	 * 
	 * @param numeroArbitro
	 */
	public void setNumeroArbitro(String numeroArbitro) {
		this.numeroArbitro = numeroArbitro;
	}
	
	/**
	 * 
	 */
	public void clearFields() {
		designation = number = numeroArbitro = "";
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterable<? extends IMatch> getSport() {
		try {
			addSportsEventServicesHandler.selectSportEvent(designation);
			return addSportsEventServicesHandler.getMatchesCurrentSEWithMissingReferees();
		} catch (ApplicationException e) {
			return new LinkedList<IMatch> ();		
		}
	}
	
}
