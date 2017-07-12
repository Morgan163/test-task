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
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
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

        final VerticalLayout doctorLayout;

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

        final HorizontalLayout doctorButtonLayout = new HorizontalLayout();
        final HorizontalLayout patientButtonLayout = new HorizontalLayout();
        final HorizontalLayout recipeButtonLayout = new HorizontalLayout();

        final Button addDoctorButton = new Button("Добавить");
        final Button changeDoctorButton = new Button("Изменить");
        final Button deleteDoctorButton = new Button("Удалить");

        final Button addPatientButton = new Button("Добавить");
        final Button changePatientButton = new Button("Изменить");
        final Button deletePatientButton = new Button("Удалить");

        final Button addRecipeButton = new Button("Добавить");
        final Button changeRecipeButton = new Button("Изменить");
        final Button deleteRecipeButton = new Button("Удалить");


        doctorLayout.setSizeFull();
        doctorButtonLayout.setWidth("400px");
        patientLayout.setSizeFull();
        recipeLayout.setSizeFull();
        tabLayout.setSizeFull();

        doctorLayout.setMargin(true);
        patientLayout.setMargin(true);
        recipeLayout.setMargin(true);

        recipeGrid.setWidth("1200px");

        doctorButtonLayout.addComponent(addDoctorButton);
        doctorButtonLayout.addComponent(changeDoctorButton);
        doctorButtonLayout.addComponent(deleteDoctorButton);

        patientButtonLayout.addComponent(addPatientButton);
        patientButtonLayout.addComponent(changePatientButton);
        patientButtonLayout.addComponent(deletePatientButton);

        recipeButtonLayout.addComponent(addRecipeButton);
        recipeButtonLayout.addComponent(changeRecipeButton);
        recipeButtonLayout.addComponent(deleteRecipeButton);

        doctorLayout.addComponent(doctorsGrid);
        doctorLayout.addComponent(doctorButtonLayout);

        patientLayout.addComponent(patientGrid);
        patientLayout.addComponent(patientButtonLayout);

        recipeLayout.addComponent(recipeGrid);
        recipeLayout.addComponent(recipeButtonLayout);

        tabLayout.addComponent(tabSheet);

        tabSheet.setSizeFull();

        tabSheet.addTab(doctorLayout,"Доктора");
        tabSheet.addTab(patientLayout,"Пациенты");
        tabSheet.addTab(recipeLayout,"Рецепты");

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



        recipeGrid.setColumns("patientName","doctorName","description","dateOfCreate","validity","priority");
        recipeGrid.getColumn("description").setResizable(true);
        recipeGrid.getColumn("patientName").setHeaderCaption("Пациент");
        recipeGrid.getColumn("doctorName").setHeaderCaption("Доктор");
        recipeGrid.getColumn("description").setHeaderCaption("Описание рецепта");
        recipeGrid.getColumn("dateOfCreate").setHeaderCaption("Дата назначения");
        recipeGrid.getColumn("validity").setHeaderCaption("Срок действия\n  (дни)");
        recipeGrid.getColumn("priority").setHeaderCaption("Приоритет");



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
            GeneratedPropertyContainer container = new GeneratedPropertyContainer(beanItemContainer);
            container.addGeneratedProperty("patientName", new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object o, Object o1) {
                    String name = (String)item.getItemProperty("patient.name").getValue();
                    String surname = (String)item.getItemProperty("patient.surname").getValue();
                    String secondName = (String)item.getItemProperty("patient.secondName").getValue();
                    return surname+" "+name.substring(0,1)+". "+secondName.substring(0,1)+".";
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });
            container.addGeneratedProperty("doctorName", new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object o, Object o1) {
                    String name = (String)item.getItemProperty("doctor.name").getValue();
                    String surname = (String)item.getItemProperty("doctor.surname").getValue();
                    String secondName = (String)item.getItemProperty("doctor.secondName").getValue();
                    return surname+" "+name.substring(0,1)+". "+secondName.substring(0,1)+".";
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });
            recipeGrid.setContainerDataSource(container);
        } catch (DataException e) {
            e.printStackTrace();
        }
    }
}