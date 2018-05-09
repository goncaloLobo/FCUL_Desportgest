package presentation.fx.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import business.Periodicity;
import facade.handlers.ISportsEventServicesRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewSportEventModel {

	private final StringProperty designation;
	private final ObjectProperty<String> selectedType;
	private final ObjectProperty<Periodicity> selectedPeriodicity;
	private final ObservableList<Periodicity> periodicities;
	private final ObservableList<String> tipos;
	private final SimpleObjectProperty<LocalDate> data;
	private final IntegerProperty numParts;
	private final IntegerProperty numReferee;

	public NewSportEventModel(ISportsEventServicesRemote sesr) {
		designation = new SimpleStringProperty();
		numParts = new SimpleIntegerProperty();
		numReferee = new SimpleIntegerProperty();
		data = new SimpleObjectProperty<LocalDate>(null);
		this.periodicities = FXCollections.observableArrayList();

		Arrays.stream(Periodicity.values()).forEach(p -> periodicities.add(p));
		this.tipos = FXCollections.observableArrayList();
		tipos.add("Championship");
		tipos.add("Cup");

		selectedPeriodicity = new SimpleObjectProperty<>(null);
		selectedType = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<Periodicity> selectedPeriodicityProperty() {
		return selectedPeriodicity;
	}

	public final Periodicity getSelectedPeriodicity() {
		return selectedPeriodicity.get();
	}

	public final void setSelectedPeriodicity(Periodicity p) {
		selectedPeriodicity.set(p);
	}

	public ObservableList<Periodicity> getPeriodicities() {
		return periodicities;
	}

	public ObjectProperty<String> selectedTypeProperty() {
		return selectedType;
	}

	public final String getSelectedType() {
		return selectedType.get();
	}

	public final void setType(String s) {
		selectedType.set(s);
	}

	public ObservableList<String> getTypes() {
		return tipos;
	}

	public String getDesignation() {
		return designation.get();
	}

	public StringProperty designationProperty() {
		return designation;
	}

	public int getNumParts() {
		return numParts.get();
	}

	public IntegerProperty numPartsProperty() {
		return numParts;
	}

	public int getNumReferees() {
		return numReferee.get();
	}

	public IntegerProperty numRefereeProperty() {
		return numReferee;
	}

	public SimpleObjectProperty<LocalDate> selectedDateProperty() {
		return data;
	}

	public final Date getSelectedDate() {
		return convertToDate(data.get());
	}

	public final void setSelectedDate(LocalDate d) {
		data.set(d);
	}

	private Date convertToDate(LocalDate ld) {
		return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public void clearProperties() {
		designation.set("");
		numParts.set(0);
		numReferee.set(0);
		selectedPeriodicity.set(null);
		data.set(null);
		selectedType.set(null);
	}
}
