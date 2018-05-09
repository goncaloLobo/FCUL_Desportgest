package facade.interfaces;

import java.io.Serializable;

/**
 *
 * @author Thibault Langlois
 */
public interface IReferee extends Serializable {
    
    /**
     * @return the referee's license number
     */
    int getLicenseNumber();
    
    
}
