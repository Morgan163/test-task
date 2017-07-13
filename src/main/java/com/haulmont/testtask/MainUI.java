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
import com.haulmont.testtask.windows.RecipeWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ConnectionToDb connectionToDb;

    private DoctorsController doctorsController;
    private PatientsController patientsController;
    private RecipesController recipesController;

    final VerticalLayout doctorLayout = new VerticalLayout();
    final VerticalLayout patientLayout = new VerticalLayout();
    final VerticalLayout recipeLayout = new VerticalLayout();

    final HorizontalLayout doctorButtonLayout = new HorizontalLayout();
    final HorizontalLayout patientButtonLayout = new HorizontalLayout();
    final HorizontalLayout recipeButtonLayout = new HorizontalLayout();

    final HorizontalLayout filterLayout = new HorizontalLayout();

    private final Grid doctorsGrid;
    private final Grid patientGrid;
    private final Grid recipeGrid;

    final Button addDoctorButton = new Button("Добавить");
    final Button changeDoctorButton = new Button("Изменить");
    final Button deleteDoctorButton = new Button("Удалить");
    final Button doctorStatisticButton = new Button("Статистика");

    final Button addPatientButton = new Button("Добавить");
    final Button changePatientButton = new Button("Изменить");
    final Button deletePatientButton = new Button("Удалить");

    final Button addRecipeButton = new Button("Добавить");
    final Button changeRecipeButton = new Button("Изменить");
    final Button deleteRecipeButton = new Button("Удалить");

    final Label filter = new Label("Фильтр");
    final ComboBox patientsBoxFilter = new ComboBox("Пациент");
    final NativeSelect priorityFilter = new NativeSelect("Приоритет");
    final TextField descriptionFilter = new TextField("Описание");
    final Button applyFilter = new Button("Применить");
    final Button cancelFilter = new Button("Отменить");

    private TabSheet tabSheet;
    private enum PriorityEnum {
        Нормальный{
            @Override
            public String toString() {
                return "Нормальный";
            }
        },
        Срочный{
            @Override
            public String toString() {
                return "Срочный";
            }
        },
        Немедленный{
            @Override
            public String toString() {
                return "Немедленный";
            }
        }};

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




        addDoctorButton.addClickListener(e->addDoctorButtonListener());
        changeDoctorButton.addClickListener(e->changeDoctorButtonListener());
        deleteDoctorButton.addClickListener(e->deleteDoctorButtonListener());
        doctorStatisticButton.addClickListener(e->getStatisticListener());


        addPatientButton.addClickListener(e -> addPatientButtonListener());
        changePatientButton.addClickListener(e -> changePatientButtonListener());
        deletePatientButton.addClickListener(e -> deletePatientButtonListener());


        addRecipeButton.addClickListener(e->addRecipeButtonListener());
        changeRecipeButton.addClickListener(e -> changeRecipeButtonListener());
        deleteRecipeButton.addClickListener(e -> deleteRecipeButtonListener());

        patientsBoxFilter.setContainerDataSource(getPatientContainer());
        patientsBoxFilter.setNullSelectionItemId(new Patient(0,"","","",0));
        patientsBoxFilter.setItemCaptionPropertyId("patientName");

        priorityFilter.addItems(PriorityEnum.values());
        priorityFilter.setNullSelectionItemId("");

        applyFilter.addClickListener(e -> applyFilterListener());
        cancelFilter.addClickListener(e -> cancelFilterListener());


        doctorLayout.setSizeFull();
        doctorButtonLayout.setSizeFull();
        doctorButtonLayout.setWidth("600px");

        patientLayout.setSizeFull();
        patientButtonLayout.setSizeFull();
        patientButtonLayout.setWidth("400px");

        recipeLayout.setSizeFull();
        recipeButtonLayout.setSizeFull();
        recipeButtonLayout.setWidth("400px");

        tabLayout.setSizeFull();


        doctorButtonLayout.setMargin(true);
        doctorButtonLayout.setSpacing(true);
        patientButtonLayout.setMargin(true);
        patientButtonLayout.setSpacing(true);
        recipeButtonLayout.setMargin(true);
        recipeButtonLayout.setSpacing(true);
        doctorLayout.setMargin(true);
        patientLayout.setMargin(true);
        recipeLayout.setMargin(true);
        filterLayout.setMargin(true);
        filterLayout.setSpacing(true);

        filterLayout.setHeight("10px");

        filterLayout.addComponents(filter,patientsBoxFilter,priorityFilter,descriptionFilter,applyFilter,cancelFilter);


        doctorButtonLayout.addComponents(addDoctorButton,changeDoctorButton,
                deleteDoctorButton, doctorStatisticButton);

        patientButtonLayout.addComponents(addPatientButton,changePatientButton,
                deletePatientButton);

        recipeButtonLayout.addComponents(addRecipeButton,changeRecipeButton,
                deleteRecipeButton);

        doctorLayout.addComponents(doctorsGrid,doctorButtonLayout);
        doctorLayout.getExpandRatio(doctorButtonLayout);

        patientLayout.addComponents(patientGrid,patientButtonLayout);

        recipeLayout.addComponents(filterLayout,recipeGrid,recipeButtonLayout);

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
        try {
            updateRecipes(recipesController.getRecipes());
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }

    }

    public void updateDoctors() {

        Set<Doctor> doctors = null;

        try {
            doctors = doctorsController.getDoctors();
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        doctorsGrid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctors));
    }

    public void updatePatients(){
        Set<Patient> patients = null;
        try {
            patients = patientsController.getPatients();
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        patientGrid.setContainerDataSource(new BeanItemContainer<>(Patient.class,patients));
    }

    public void updateRecipes(Set<Recipe> recipes) {
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

    }

    private GeneratedPropertyContainer getPatientContainer(){
        GeneratedPropertyContainer container = null;
        try {
            final BeanItemContainer<Patient> beanItemContainer = new BeanItemContainer<>(Patient.class, patientsController.getPatients());
            container = new GeneratedPropertyContainer(beanItemContainer);
            container.addGeneratedProperty("patientName", new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object o, Object o1) {
                    String name = (String)item.getItemProperty("name").getValue();
                    String surname = (String)item.getItemProperty("surname").getValue();
                    String secondName = (String)item.getItemProperty("secondName").getValue();
                    return surname+" "+name.substring(0,1)+". "+secondName.substring(0,1)+".";
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });

        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        return container;
    }

    private void applyFilterListener(){
        long id;
        String priority = "";
        String description = "";
        if(patientsBoxFilter.getValue()!=null){
            id = ((Patient)patientsBoxFilter.getValue()).getId();
        }else{
            id = 0;
        }
        if(priorityFilter.getValue()!=null){
            priority = ((PriorityEnum)priorityFilter.getValue()).toString();
        }
        description = descriptionFilter.getValue();
        try {
            updateRecipes(recipesController.getRecipesAfterFilter(id,priority,description, recipesController.getRecipes()));
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancelFilterListener(){
        try {
            updateRecipes(recipesController.getRecipes());
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
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
            Notification.show("Строка для удаления не выбрана");
        }else {
            try {
                doctorsController.deleteDoctor(doctor);
                updateDoctors();
            } catch (DeleteDataException e) {
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    private void getStatisticListener(){
        FormLayout verticalLayout = new FormLayout();
        verticalLayout.setSizeFull();
        Grid statisticGrid = new Grid();
        statisticGrid.setSizeFull();
        statisticGrid.setWidth("430px");;
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
            window.setWidth("500px");
            window.setClosable(true);
            verticalLayout.setMargin(new MarginInfo(true,true,true,true));
            verticalLayout.setSpacing(true);
            verticalLayout.addComponent(statisticGrid);
            //verticalLayout.setComponentAlignment(statisticGrid,Alignment.MIDDLE_CENTER);
            window.setContent(statisticGrid);

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
            Notification.show("Строка для удаления не выбрана");
        }else {
            try {
                patientsController.deletePatient(patient);
                updatePatients();
            } catch (DeleteDataException e) {
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    private void addRecipeButtonListener(){
        RecipeWindow recipeWindow = new RecipeWindow(this, null, "add");
        recipeWindow.center();
        recipeWindow.setClosable(false);
        recipeWindow.setModal(true);
        recipeWindow.setHeight("600px");
        recipeWindow.setWidth("500px");
        UI.getCurrent().addWindow(recipeWindow);
    }

    private void changeRecipeButtonListener(){
        Recipe recipe = (Recipe) recipeGrid.getSelectedRow();
        if(recipe==null){
            Notification.show("Строка для изменения не выбрана");
        }else{
            RecipeWindow recipeWindow = new RecipeWindow(this, recipe, "change");
            recipeWindow.center();
            recipeWindow.setClosable(false);
            recipeWindow.setModal(true);
            recipeWindow.setHeight("600px");
            recipeWindow.setWidth("400px");
            UI.getCurrent().addWindow(recipeWindow);
        }
    }

    private void deleteRecipeButtonListener(){
        Recipe recipe = (Recipe) recipeGrid.getSelectedRow();
        if(recipe==null){
            Notification.show("Строка для удаления не выбрана");
        }else{
            try {
                recipesController.deleteRecipe(recipe);
                updateRecipes(recipesController.getRecipes());
            } catch (DeleteDataException e) {
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } catch (DataException e) {
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        }
    }
}