package com.haulmont.testtask;

import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.controllers.PatientsController;
import com.haulmont.testtask.controllers.RecipesController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.DataException;
import com.haulmont.testtask.exceptions.DeleteDataException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.windows.DoctorWindow;
import com.haulmont.testtask.windows.PatientWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.*;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
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

        final VerticalLayout doctorLayout;

        doctorsGrid = new Grid();
        doctorsGrid.setSizeFull();
        patientGrid = new Grid();
        patientGrid.setSizeFull();
        recipeGrid = new Grid();
        recipeGrid.setSizeFull();

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
        addDoctorButton.addClickListener(e->addDoctorButtonListener());
        final Button changeDoctorButton = new Button("Изменить");
        changeDoctorButton.addClickListener(e->changeDoctorButtonListener());
        final Button deleteDoctorButton = new Button("Удалить");
        deleteDoctorButton.addClickListener(e->deleteDoctorButtonListener());
        final Button doctorStatisticButton = new Button("Статистика");
        doctorStatisticButton.addClickListener(e->getStatisticListener());

        final Button addPatientButton = new Button("Добавить");
        addPatientButton.addClickListener(e -> addPatientButtonListener());
        final Button changePatientButton = new Button("Изменить");
        changePatientButton.addClickListener(e -> changePatientButtonListener());
        final Button deletePatientButton = new Button("Удалить");
        deletePatientButton.addClickListener(e -> deletePatientButtonListener());

        final Button addRecipeButton = new Button("Добавить");
        final Button changeRecipeButton = new Button("Изменить");
        final Button deleteRecipeButton = new Button("Удалить");


        doctorLayout.setSizeFull();
        doctorButtonLayout.setSizeFull();
        doctorButtonLayout.setWidth("500px");

        patientLayout.setSizeFull();
        patientButtonLayout.setSizeFull();
        patientButtonLayout.setWidth("400px");

        recipeLayout.setSizeFull();
        recipeButtonLayout.setSizeFull();
        recipeButtonLayout.setWidth("400px");

        tabLayout.setSizeFull();

        doctorLayout.setMargin(true);
        patientLayout.setMargin(true);
        recipeLayout.setMargin(true);


        doctorButtonLayout.addComponents(addDoctorButton,changeDoctorButton,
                deleteDoctorButton, doctorStatisticButton);

        patientButtonLayout.addComponents(addPatientButton,changePatientButton,
                deletePatientButton);

        recipeButtonLayout.addComponents(addRecipeButton,changeRecipeButton,
                deleteRecipeButton);

        doctorLayout.addComponents(doctorsGrid,doctorButtonLayout);

        patientLayout.addComponents(patientGrid,patientButtonLayout);

        recipeLayout.addComponents(recipeGrid,recipeButtonLayout);

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

    public void updateDoctors() {

        Set<Doctor> doctors = null;

        try {
            doctors = doctorsController.getDoctors();
            ;
        } catch (DataException e) {
            e.printStackTrace();
        }
        doctorsGrid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctors));
    }

    public void updatePatients(){
        Set<Patient> patients = null;
        try {
            patients = patientsController.getPatients();
        } catch (DataException e) {
            e.printStackTrace();
        }
        patientGrid.setContainerDataSource(new BeanItemContainer<>(Patient.class,patients));
    }

    public void updateRecipes() {
        Set<Recipe> recipes = null;
        try {
            recipes = recipesController.getRecipes();

            final BeanItemContainer<Recipe> beanItemContainer = new BeanItemContainer<>(Recipe.class, recipes);
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

    private void addDoctorButtonListener() {
        DoctorWindow doctorWindow = new DoctorWindow(this, null, "add");
        doctorWindow.center();
        doctorWindow.setClosable(false);
        doctorWindow.setModal(true);
        doctorWindow.setHeight("300px");
        doctorWindow.setWidth("400px");
        UI.getCurrent().addWindow(doctorWindow);
    }

    private void changeDoctorButtonListener(){
        Doctor doctor = (Doctor) doctorsGrid.getSelectedRow();
        if(doctor==null){
            Notification.show("Строка для изменения не выбрана");
        }else {
            DoctorWindow doctorWindow = new DoctorWindow(this, doctor, "change");
            doctorWindow.center();
            doctorWindow.setClosable(false);
            doctorWindow.setModal(true);
            doctorWindow.setHeight("300px");
            doctorWindow.setWidth("400px");
            UI.getCurrent().addWindow(doctorWindow);
        }
    }

    private void deleteDoctorButtonListener(){
        Doctor doctor = (Doctor) doctorsGrid.getSelectedRow();
        if(doctor==null){
            Notification.show("Строка для изменения не выбрана");
        }else {
            try {
                doctorsController.deleteDoctor(doctor);
                updateDoctors();
            } catch (DeleteDataException e) {
                Notification.show(e.getMessage());
            }
        }
    }

    private void getStatisticListener(){
        FormLayout verticalLayout = new FormLayout();
        Grid statisticGrid = new Grid();
        statisticGrid.setSizeFull();
        statisticGrid.setWidth("300px");
        statisticGrid.addColumn("doctor",String.class).setHeaderCaption("Доктор");
        statisticGrid.addColumn("numberOfRecipes", Integer.class).setHeaderCaption("Кол-во рецептов");
        try {
            Map<Doctor,Integer> statistic = doctorsController.getStatistic();
            for (Map.Entry<Doctor,Integer> entry:statistic.entrySet()){
                statisticGrid.addRow(entry.getKey().getSurname()+" "
                        +entry.getKey().getName().substring(0,1)+". "
                        +entry.getKey().getSecondName().substring(0,1)+".",entry.getValue());
            }
            Window window = new Window();
            window.setModal(true);
            window.setSizeFull();
            window.center();
            window.setHeight("600px");
            window.setWidth("400px");
            window.setClosable(true);
            verticalLayout.addComponent(statisticGrid);
            window.setContent(verticalLayout);
            UI.getCurrent().addWindow(window);
        } catch (DataException e) {
            Notification.show(e.getMessage());
        }
    }

    private void addPatientButtonListener(){
        PatientWindow patientWindow = new PatientWindow(this, null, "add");
        patientWindow.center();
        patientWindow.setClosable(false);
        patientWindow.setModal(true);
        patientWindow.setHeight("300px");
        patientWindow.setWidth("400px");
        UI.getCurrent().addWindow(patientWindow);
    }

    private void changePatientButtonListener(){
        Patient patient = (Patient) patientGrid.getSelectedRow();
        if(patient==null){
            Notification.show("Строка для изменения не выбрана");
        }else {
            PatientWindow patientWindow = new PatientWindow(this, patient, "change");
            patientWindow.center();
            patientWindow.setClosable(false);
            patientWindow.setModal(true);
            patientWindow.setHeight("300px");
            patientWindow.setWidth("400px");
            UI.getCurrent().addWindow(patientWindow);
        }
    }

    private void deletePatientButtonListener(){
       Patient patient = (Patient) patientGrid.getSelectedRow();
        if(patient==null){
            Notification.show("Строка для изменения не выбрана");
        }else {
            try {
                patientsController.deletePatient(patient);
                updatePatients();
            } catch (DeleteDataException e) {
                Notification.show(e.getMessage());
            }
        }
    }
}