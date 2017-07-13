package com.haulmont.testtask.UI.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.PatientsController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.AddDataException;
import com.haulmont.testtask.exceptions.ChangeDataException;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.windows.PatientWindow;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

/**
 * Created by andrei on 12.07.17.
 */
public class PatientForm extends FormLayout {

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private Patient patient;
    private  String action;
    private MainUI mainUI;
    private PatientWindow patientWindow;
    private PatientsController patientsController;
    private ConnectionToDb connectionToDb;

    private TextField nameText = new TextField("Имя");
    private TextField surNameText = new TextField("Фамилия");
    private TextField secondNameText = new TextField("Отчество");
    private TextField phoneNumberText = new TextField("Номер телефона");
    private Button ok = new Button("ОК");
    private Button cancel = new Button("Отменить");

    public PatientForm(Patient patient, String action, MainUI mainUI, PatientWindow patientWindow) {
        this.patient = patient;
        this.action = action;
        this.mainUI = mainUI;
        this.patientWindow = patientWindow;

        if(action.equals("change")){
            nameText.setValue(patient.getName());
            surNameText.setValue(patient.getSurname());
            secondNameText.setValue(patient.getSecondName());
            phoneNumberText.setValue(String.valueOf(patient.getPhoneNumber()));
        }

        try {
            connectionToDb = new ConnectionToDb();
            patientsController = new PatientsController(connectionToDb);
        } catch (SQLException e) {
            Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }

        setSizeFull();
        setMargin(true);
        buttonLayout.setWidth("300px");
        buttonLayout.setSizeFull();
        buttonLayout.addComponents(ok,cancel);
        addComponents(nameText,surNameText,secondNameText,phoneNumberText,buttonLayout);


        ok.setStyleName(ValoTheme.BUTTON_PRIMARY);

        ok.addClickListener(e-> savePatient());
        cancel.addClickListener(e->cancel());
    }

    private void savePatient() {
        try {
            if (action.equals("add")) {

                patientsController.addPatient(nameText.getValue(), surNameText.getValue(),
                        secondNameText.getValue(), phoneNumberText.getValue());

            }
            if (action.equals("change")) {

                patientsController.changePatient(patient.getId(), nameText.getValue(), surNameText.getValue(),
                        secondNameText.getValue(), phoneNumberText.getValue());

            }
            mainUI.updatePatients();
            patientWindow.close();
        } catch (ChangeDataException | AddDataException e) {
            Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel(){
        patientWindow.close();
    }
}
