package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.handlers.ISportsEventServicesRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.NewSportEventController;
import presentation.fx.model.NewSportEventModel;

public class Startup extends Application {
    
	private static ISportsEventServicesRemote SportEventServices;

	@Override 
    public void start(Stage stage) throws IOException {
		
		// This line to resolve keys against Bundle.properties
        ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
        FXMLLoader createSportEventLoader = new FXMLLoader(getClass().getResource("/fxml/CreateSportEvent.fxml"), i18nBundle);
    	Parent root = createSportEventLoader.load();
    	NewSportEventController newSportsEventController = createSportEventLoader.getController();
    	
    	NewSportEventModel newSportsEventModel = new NewSportEventModel(SportEventServices);
    	newSportsEventController.setModel(newSportsEventModel);
    	newSportsEventController.setSportsEventServices(SportEventServices);
    	newSportsEventController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 450, 275);
       
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(ISportsEventServicesRemote addSportsEventServicesHandler) {
		Startup.SportEventServices = addSportsEventServicesHandler;
        launch();
	}
}
