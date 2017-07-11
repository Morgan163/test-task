package com.haulmont.testtask;

import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.DataException;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ConnectionToDb connectionToDb = new ConnectionToDb();
    private DoctorsController doctorsController = new DoctorsController(connectionToDb);
    private Grid doctorsGrid = new Grid();

    public MainUI() throws SQLException {
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();


        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(doctorsGrid);
        setContent(layout);
        doctorsGrid.setColumns("surname","name","secondName","specialization");
        updateDoctors();

    }

    private void updateDoctors() {

        Set<Doctor> doctors = null;
        try {
            doctors = doctorsController.getDoctors();
        } catch (DataException e) {
            e.printStackTrace();
        }
        doctorsGrid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctors));
    }
}