package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import application.ArbitroService;
import application.CalendarioEncontrosService;
import application.ProvaService;
import business.AdicionaProvaHandler;
import business.AssociarArbitroHandler;
import business.CalendarioArbitroHandler;
import facade.exceptions.ApplicationException;

/**
 * Implements the startup use case. It creates the application, 
 * a simple client that interacts with the application, passing
 * it the application handlers. 
 * 
 * @author css013
 *
 */
public class DesporGes {

	private ArbitroService arbitroService;
	private ProvaService provaService;
	private EntityManagerFactory emf;

    public void run() throws ApplicationException {
        // Connects to the database
        try {
    		emf = Persistence.createEntityManagerFactory("domain-model-jpa");
            arbitroService = new ArbitroService(new AssociarArbitroHandler(emf));
            provaService = new ProvaService(new AdicionaProvaHandler(emf));
            // exceptions thrown by JPA are not checked
        } catch (Exception e) {
            throw new ApplicationException("Error connecting database", e);
        }
    }

    public void stopRun() {
        // Closes the database connection
    	emf.close();
    }

    public ArbitroService getArbitroService() {
        return arbitroService;
    }
    
    public ProvaService getProvaService() {
        return provaService;
    }

    public CalendarioEncontrosService getCalendarioEncontrosService() {
    	// always provides a new service because the service (in fact the CalendarioArbitroHandler) it knows about the 
    	// current referee (it is stateful)
        return new CalendarioEncontrosService(new CalendarioArbitroHandler(emf));
    }
}
