package client;

import javax.ejb.EJB;

import facade.handlers.ISportsEventServicesRemote;

/**
 * A simple application client that uses both services.
 *
 * @author fmartins
 * @version 1.2 (11/02/2015)
 */
public class Main {
		
	// run with the following command inside the desporgest project directory:
	// PATH-TO-WILDFLY/bin/appclient.sh desporgest-ear/target/desporgest-ear-1.0.ear#desporgest-gui-client.jar
	// Example in case the wildfly server is in your hamedir in dir wildfly-10.1.0.Final
	// ~/wildfly-10.1.0.Final/bin/appclient.sh desporgest-ear/target/desporgest-ear-1.0.ear#desporgest-gui-client.jar
	
    
    @EJB private static ISportsEventServicesRemote addSportsEventServicesRemote;

	/**
	 * An utility class should not have public constructors
	 */
	private Main() {
	}

    /**
     * A simple interaction with the application services
     *
     * @param args Command line parameters
     */
    public static void main(String[] args) {
    	presentation.fx.Startup.startGUI(addSportsEventServicesRemote);
    }
}