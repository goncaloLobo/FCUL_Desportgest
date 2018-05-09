package presentation.fx.inputcontroller;

import java.util.Calendar;
import java.util.Date;
import java.util.function.UnaryOperator;

import business.Periodicity;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import facade.exceptions.ApplicationException;
import facade.handlers.ISportsEventServicesRemote;
import presentation.fx.model.NewSportEventModel;

public class NewSportEventController extends BaseController {

	@FXML
	private TextField designationTextField;

	@FXML
	private ComboBox<String> typeComboBox;

	@FXML
	private ComboBox<Periodicity> periodicityComboBox;

	@FXML
	private DatePicker dateTextField;

	@FXML
	private TextField numPartsTextField;

	@FXML
	private TextField numRefereeTextField;

	private NewSportEventModel model;

	private ISportsEventServicesRemote sportsEventServices;

	public void setSportsEventServices(ISportsEventServicesRemote sportsEventServices) {
		this.sportsEventServices = sportsEventServices;
	}

	public void setModel(NewSportEventModel model) {
		this.model = model;
		
		designationTextField.textProperty().bindBidirectional(model.designationProperty());
		
		typeComboBox.setItems(model.getTypes());
		typeComboBox.setValue(model.getSelectedType());
		
		periodicityComboBox.setItems(model.getPeriodicities());
		periodicityComboBox.setValue(model.getSelectedPeriodicity());
		
		numPartsTextField.textProperty().bindBidirectional(model.numPartsProperty(), new NumberStringConverter());
		numRefereeTextField.textProperty().bindBidirectional(model.numRefereeProperty(), new NumberStringConverter());
	}

	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};

		numPartsTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		numRefereeTextField
				.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
	}

	@FXML
	void createSportEventAction(ActionEvent action) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				// se eh championship ou else

				if (model.getSelectedType().equals("Championship")) {
					sportsEventServices.createChampionshipSportEvent(model.getDesignation(), model.getNumParts(),
							model.getNumReferees(), toCalendar(model.getSelectedDate()),
							model.getSelectedPeriodicity());
				} else {
					sportsEventServices.createCupSportEvent(model.getDesignation(), model.getNumParts(),
							model.getNumReferees(), toCalendar(model.getSelectedDate()),
							model.getSelectedPeriodicity());
				}
				model.clearProperties();
				showInfo(i18nBundle.getString("new.sportEvent.success"));
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.sportEvent.error.adding") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("new.sportEvent.error.validating") + ":\n" + errorMessages);
	}

	private String validateInput() {
		StringBuilder sb = new StringBuilder();
		String designation = model.getDesignation();
		if (designation == null || designation.length() == 0) {
			sb.append(i18nBundle.getString("new.sportEvent.invalid.designation"));
		}
		if (model.getNumParts() == 0) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(i18nBundle.getString("new.sportEvent.invalid.numParts"));
		}
		if (model.getNumReferees() == 0) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(i18nBundle.getString("new.sportEvent.invalid.numReferees"));
		}
		if (model.getSelectedPeriodicity() == null) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(i18nBundle.getString("new.sportEvent.invalid.periodicity"));
		}
		if (model.getSelectedType() == null) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(i18nBundle.getString("new.sportEvent.invalid.type"));
		}

		return sb.toString();
	}

	@FXML
	void datePicker(ActionEvent event) {
		model.setSelectedDate(dateTextField.getValue());
	}

	@FXML
	void periodicitySelected(ActionEvent event) {
		model.setSelectedPeriodicity(periodicityComboBox.getValue());
	}

	@FXML
	void typeSelected(ActionEvent event) {
		model.setType(typeComboBox.getValue());
	}

	private static Calendar toCalendar(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

}
