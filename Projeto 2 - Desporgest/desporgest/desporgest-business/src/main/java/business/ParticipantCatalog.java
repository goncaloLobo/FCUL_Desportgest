package business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

/**
 *
 * @author Thibault Langlois
 */
@Stateless
public class ParticipantCatalog {

    public List<Participant> makeParticipants(int nParticipants) {
        ArrayList<Participant> set = new ArrayList<>();
        for (int i = 0; i < nParticipants; i++) {
            Participant p = new Participant();
            set.add(p);
        }
        return set;
    }
}
