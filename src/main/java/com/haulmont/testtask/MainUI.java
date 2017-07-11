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
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.awt.*;
import java.sql.SQLException;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ConnectionToDb connectionToDb;

    private DoctorsController doctorsController;
    private PatientsController patientsController;
    private RecipesController recipesController;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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

        recipeGrid.setWidth("1200px");


        doctorLayout.addComponent(doctorsGrid);
        patientLayout.addComponent(patientGrid);
        recipeLayout.addComponent(recipeGrid);
        tabLayout.addComponent(tabSheet);

        tabSheet.setSizeFull();

        tabSheet.addTab(doctorLayout,"Doctors");
        tabSheet.addTab(patientLayout,"Patients");
        tabSheet.addTab(recipeLayout,"Recipes");

        doctorsGrid.setColumns("surname","name","secondName","specialization");
        doctorsGrid.getColumn("surname").setHeaderCaption("Фамилия");
        doctorsGrid.getColumn("name").setHeaderCaption("Имя");
        doctorsGrid.getColumn("secondName").setHeaderCaption("Отчество");
        doctorsGrid.getColumn("specialization").setHeaderCaption("Специализация");

        patientGrid.setColumns("surname","name","secondName","phoneNumber");
        patientGrid.getColumn("surname").setHeaderCaption("Фамилия");
        patientGrid.getColumn("name").setHeaderCaption("Имя");
        patientGrid.getColumn("secondName").setHeaderCaption("Отчество");
        patientGrid.getColumn("phoneNumber").setHeaderCaption("Номер телефона");

        recipeGrid.setColumns("patient.surname","patient.name","patient.secondName",
                "doctor.surname","doctor.name","doctor.secondName","description","dateOfCreate","validity","priority");
        recipeGrid.getColumn("description").setMaximumWidth(100);
        setContent(tabLayout);

        updateDoctors();
        updatePatients();
        updateRecipes();

    }

    private void updateDoctors() {

        Set<Doctor> doctors = null;

        try {
            doctors = doctorsController.getDoctors();
            ;
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

    private void updateRecipes() {
        Set<Recipe> recipes = null;
        try {
            recipes = recipesController.getRecipes();
            final BeanItemContainer<Recipe> beanItemContainer = new BeanItemContainer<Recipe>(Recipe.class, recipes);
            beanItemContainer.addNestedContainerBean("patient");
            beanItemContainer.addNestedContainerBean("doctor");
            recipeGrid.setContainerDataSource(beanItemContainer);
        } catch (DataException e) {
            e.printStackTrace();
        }
    }
}