package com.haulmont.testtask.windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.UI.forms.PatientForm;
import com.haulmont.testtask.model.Patient;
import com.vaadin.ui.Window;

/**
 * Created by andrei on 12.07.17.
 */
public class PatientWindow extends Window {

    public PatientWindow(MainUI mainUI, Patient patient, String action){
        PatientForm patientForm = new PatientForm(patient,action, mainUI, this);
        setContent(patientForm);

    }
}
