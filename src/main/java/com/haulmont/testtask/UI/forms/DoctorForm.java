package com.haulmont.testtask.UI.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.AddDataException;
import com.haulmont.testtask.exceptions.ChangeDataException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.windows.DoctorWindow;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

/**
 * Created by andrei on 12.07.17.
 */
public class DoctorForm extends FormLayout {

    private HorizontalLayout buttonLayout = new HorizontalLayout();


    private Doctor doctor;
    private  String action;
    private MainUI mainUI;
    private DoctorWindow doctorWindow;
    private DoctorsController doctorsController;
    private ConnectionToDb connectionToDb;

    private TextField nameText = new TextField("Имя");
    private TextField surNameText = new TextField("Фамилия");
    private TextField secondNameText = new TextField("Отчество");
    private TextField specializationText = new TextField("Специализация");
    private Button ok = new Button("ОК");
    private Button cancel = new Button("Отменить");



    public DoctorForm(MainUI mainUi, DoctorWindow doctorWindow, Doctor doctor, String action) {
        this.mainUI = mainUi;
        this.doctor = doctor;
        this.action = action;
        this.doctorWindow = doctorWindow;

        if(action.equals("change")){
            nameText.setValue(doctor.getName());
            surNameText.setValue(doctor.getSurname());
            secondNameText.setValue(doctor.getSecondName());
            specializationText.setValue(doctor.getSpecialization());
        }

        try {
            connectionToDb = new ConnectionToDb();
        } catch (SQLException e) {
            Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }
        doctorsController = new DoctorsController(connectionToDb);

        setSizeFull();
        setMargin(true);
        buttonLayout.setWidth("300px");
        buttonLayout.setSizeFull();
        buttonLayout.addComponents(ok,cancel);
        addComponents(nameText,surNameText,secondNameText,specializationText,buttonLayout);


        ok.setStyleName(ValoTheme.BUTTON_PRIMARY);

        ok.addClickListener(e-> saveDoctor());
        cancel.addClickListener(e->cancel());

    }

    private void saveDoctor() {
        try {
            if(action.equals("add")) {
                doctorsController.addDoctor(nameText.getValue(), surNameText.getValue(),
                        secondNameText.getValue(), specializationText.getValue());
            }
            if(action.equals("change")) {
                doctorsController.changeDoctor(doctor.getId(),nameText.getValue(), surNameText.getValue(),
                        secondNameText.getValue(), specializationText.getValue());
            }
            mainUI.updateDoctors();
            doctorWindow.close();
        } catch (AddDataException | ChangeDataException e) {
            Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel(){
        doctorWindow.close();
    }
}
