package com.haulmont.testtask;

import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.controllers.PatientsController;
import com.haulmont.testtask.controllers.RecipesController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.DataException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ConnectionToDb connectionToDb;

    private DoctorsController doctorsController;
    private PatientsController patientsController;
    private RecipesController recipesController;

    private final Grid doctorsGrid;
    private final Grid patientGrid;
    private final Grid recipeGrid;

    private TabSheet tabSheet;

    public MainUI() throws SQLException {
        connectionToDb = new ConnectionToDb();

        doctorsController = new DoctorsController(connectionToDb);
        patientsController = new PatientsController(connectionToDb);
        recipesController = new RecipesController(connectionToDb);

        doctorsGrid = new Grid();
        patientGrid = new Grid();
        recipeGrid = new Grid();

        tabSheet = new TabSheet();
    }

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout tabLayout = new VerticalLayout();

        final VerticalLayout doctorLayout = new VerticalLayout();
        final VerticalLayout patientLayout = new VerticalLayout();
        final VerticalLayout recipeLayout = new VerticalLayout();

        doctorLayout.setSizeFull();
        patientLayout.setSizeFull();
        recipeLayout.setSizeFull();
        tabLayout.setSizeFull();

        doctorLayout.setMargin(true);
        patientLayout.setMargin(true);
        recipeLayout.setMargin(true);

        doctorLayout.addComponent(doctorsGrid);
        patientLayout.addComponent(patientGrid);
        recipeLayout.addComponent(recipeGrid);
        tabLayout.addComponent(tabSheet);

        tabSheet.setSizeFull();

        tabSheet.addTab(doctorLayout,"Doctors");
        tabSheet.addTab(patientLayout,"Patients");
        tabSheet.addTab(recipeLayout,"Recipes");

        doctorsGrid.setColumns("surname","name","secondName","specialization");
        patientGrid.setColumns("surname","name","secondName","phoneNumber");
        recipeGrid.setColumns("description","patient_id","doctor_id","date_of_create","validity","priority");

        setContent(tabLayout);

        updateDoctors();
        updatePatients();
        //updateRecipes();

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

    private void updatePatients(){
        Set<Patient> patients = null;
        try {
            patients = patientsController.getPatients();
        } catch (DataException e) {
            e.printStackTrace();
        }
        patientGrid.setContainerDataSource(new BeanItemContainer<>(Patient.class,patients));
    }

    private void updateRecipes(){
        Set<Recipe> recipes = null;
        try {
            recipes = recipesController.getRecipes();
        } catch (DataException e) {
            e.printStackTrace();
        }
        recipeGrid.setContainerDataSource(new BeanItemContainer<>(Recipe.class,recipes));
    }
}