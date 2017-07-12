package com.haulmont.testtask.windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.UI.forms.DoctorForm;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by andrei on 12.07.17.
 */
public class DoctorWindow extends Window {

    private DoctorForm doctorForm;

    public DoctorWindow(MainUI mainUI,Doctor doctor, String action) {

        doctorForm = new DoctorForm(mainUI,this,doctor,action);

        setContent(doctorForm);

    }


}
