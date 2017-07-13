package com.haulmont.testtask.UI.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.controllers.PatientsController;
import com.haulmont.testtask.controllers.RecipesController;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.DataException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.windows.RecipeWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by andrei on 12.07.17.
 */
public class RecipeForm extends FormLayout {

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private MainUI mainUI;
    private Recipe recipe;
    private RecipeWindow recipeWindow;
    private String action;

    private ConnectionToDb connectionToDb;
    private RecipesController recipesController;
    private PatientsController patientsController;
    private DoctorsController doctorsController;


    private ComboBox patientSelect = new ComboBox("Пациент");
    private ComboBox doctorSelect = new ComboBox("Доктор");
    private TextArea descriptionText = new TextArea("Описание");
    private DateField dateField = new DateField("Дата назначения");
    private TextField validityText = new TextField("Срок действия (дни)");
    private NativeSelect prioritySelect = new NativeSelect("Приоритет");
    private Button ok = new Button("ОК");
    private Button cancel = new Button("Отменить");

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

    public RecipeForm(MainUI mainUI, Recipe recipe, RecipeWindow recipeWindow, String action ) {
        this.mainUI = mainUI;
        this.recipe = recipe;
        this.recipeWindow = recipeWindow;
        this.action = action;

        setMargin(true);
        try {
            connectionToDb = new ConnectionToDb();
            recipesController = new RecipesController(connectionToDb);
            patientsController = new PatientsController(connectionToDb);
            doctorsController = new DoctorsController(connectionToDb);
        } catch (SQLException e) {
            Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }

        prioritySelect.addItems(PriorityEnum.values());

        patientSelect.setNullSelectionItemId(false);
        initPatientSelect();

        doctorSelect.setNullSelectionItemId(false);
        initDoctorSelect();

        if(action.equals("change")){
            patientSelect.select(recipe.getPatient());
            doctorSelect.select(recipe.getDoctor());
            descriptionText.setValue(recipe.getDescription());
            dateField.setValue(recipe.getDateOfCreate());
            validityText.setValue(Integer.toString(recipe.getValidity()));
            prioritySelect.select(PriorityEnum.valueOf(recipe.getPriority()));
        }


        setSizeFull();
        setMargin(true);
        buttonLayout.setWidth("300px");
        buttonLayout.setSizeFull();
        buttonLayout.addComponents(ok,cancel);
        addComponents(patientSelect,doctorSelect,descriptionText,dateField,validityText,prioritySelect,buttonLayout);

        ok.setStyleName(ValoTheme.BUTTON_PRIMARY);

        ok.addClickListener(e-> saveRecipe());
        cancel.addClickListener(e->cancel());


    }

    private void initPatientSelect(){
        try {
            Set<Patient> patients = patientsController.getPatients();
            BeanItemContainer<Patient> beanItemContainer = new BeanItemContainer<>(Patient.class,patients);
            GeneratedPropertyContainer container = new GeneratedPropertyContainer(beanItemContainer);
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
            patientSelect.setContainerDataSource(container);
            patientSelect.setItemCaptionPropertyId("patientName");
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void initDoctorSelect(){
        try {
            Set<Doctor> doctors= doctorsController.getDoctors();
            BeanItemContainer<Doctor> beanItemContainer = new BeanItemContainer<>(Doctor.class,doctors);
            GeneratedPropertyContainer container = new GeneratedPropertyContainer(beanItemContainer);
            container.addGeneratedProperty("doctorName", new PropertyValueGenerator<String>() {

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
            doctorSelect.setContainerDataSource(container);
            doctorSelect.setItemCaptionPropertyId("doctorName");
        } catch (DataException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void saveRecipe(){
        try {
            if (patientSelect.getValue() == null) {
                throw new DataException("Вы не выбрали пациента");
            }
            if(doctorSelect.getValue()==null){
                throw new DataException("Вы не выбрали доктора");
            }
            if(prioritySelect.getValue()==null){
                throw new DataException("Вы не указали приоритет");
            }
            if(action.equals("add")){
                recipesController.addRecipe(descriptionText.getValue(),(Patient) patientSelect.getValue(),(Doctor)doctorSelect.getValue(),
                        dateField.getValue(),validityText.getValue(),((PriorityEnum) prioritySelect.getValue()).toString());
            }
            if(action.equals("change")){
                recipesController.changeRecipe(recipe.getId(),descriptionText.getValue(),(Patient) patientSelect.getValue(),(Doctor)doctorSelect.getValue(),
                        dateField.getValue(),validityText.getValue(),((PriorityEnum) prioritySelect.getValue()).toString());
            }
            mainUI.updateRecipes(recipesController.getRecipes());
            recipeWindow.close();
        }catch (DataException e){
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel(){
        recipeWindow.close();
    }

}
