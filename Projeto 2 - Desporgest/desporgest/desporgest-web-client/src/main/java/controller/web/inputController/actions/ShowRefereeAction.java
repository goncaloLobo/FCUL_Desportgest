package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewAssignRefereeModel;
import facade.exceptions.ApplicationException;
import facade.handlers.ISportsEventServicesRemote;

@Stateless
public class ShowRefereeAction extends Action {

	@EJB
	private ISportsEventServicesRemote addSportsEventHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		NewAssignRefereeModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				addSportsEventHandler.addReferee(intValue(model.getNumber()), intValue(model.getNumeroArbitro()));
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			model.addMessage("Referee successfully added.");
		} else
			model.addMessage("Error validating referee data");

		request.getRequestDispatcher("/assignReferee/assignReferee.jsp").forward(request, response);
	}

	private boolean validInput(NewAssignRefereeModel model) {

		// check if designation is filled
		boolean result = isFilled(model, model.getNumber(), "Number of sportEvent must be filled.");

		// check if VATNumber is filled and a valid number
		result &= isFilled(model, model.getNumeroArbitro(), "Number Referee must be filled");
		return result;
	}

	private NewAssignRefereeModel createHelper(HttpServletRequest request) {
		// Create the object model
		NewAssignRefereeModel model = new NewAssignRefereeModel();
		model.setShowMatches(addSportsEventHandler);

		// fill it with data from the request
		model.setNumber(request.getParameter("numberJogo"));
		model.setNumeroArbitro(request.getParameter("numberArbitro"));
		return model;
	}
}
