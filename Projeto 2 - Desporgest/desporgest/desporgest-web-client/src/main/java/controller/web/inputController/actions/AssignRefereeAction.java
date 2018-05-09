package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewAssignRefereeModel;
import facade.handlers.IRefereeServicesRemote;
import facade.handlers.ISportsEventServicesRemote;

@Stateless
public class AssignRefereeAction extends Action {
	
	@EJB private IRefereeServicesRemote addRefereeServicesHandler;
	@EJB private ISportsEventServicesRemote addSportsEventServicesHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewAssignRefereeModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			request.getRequestDispatcher("/assignReferee/assignReferee2.jsp").forward(request, response);
		} else
			model.addMessage("Error validating referee data.");
		
		
	}
	
	private boolean validInput(NewAssignRefereeModel model) {
		
		// check if designation is filled
		boolean result = isFilled(model, model.getDesignation(), "Designation must be filled.");
		return result;
	}

	private NewAssignRefereeModel createHelper(HttpServletRequest request) {
		// Create the object model
		NewAssignRefereeModel model = new NewAssignRefereeModel();
		model.setNewAssignRefereeHandler(addRefereeServicesHandler);
		model.setShowMatches(addSportsEventServicesHandler);

		// fill it with data from the request
		model.setDesignation(request.getParameter("designation"));
		return model;
	}	
}
