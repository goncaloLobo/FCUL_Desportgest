package facade.interfaces;

import java.io.Serializable;

/**
 *
 * @author Thibault Langlois
 */
public interface IParticipant extends Serializable {
    
    /**
     * @return the participant's name
     */
    public String getName();
    
}
