package com.haulmont.testtask.controllers;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DAOImpl.DAOAbstractFactoryImpl;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.*;
import com.haulmont.testtask.model.Recipe;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by anlu0816 on 7/10/2017.
 */
public class RecipesController extends AbstractController {
    private static final int DESCRIPTION_LIMIT = 2000;
    private static final int PRIORITY_LIMIT = 20;

    private ConnectionToDb connectionToDb;
    private DAOAbstractFactory daoAbstractFactory;
    private RecipeDAO recipeDAO;

    public RecipesController(ConnectionToDb connectionToDb) {
        this.connectionToDb = connectionToDb;
        daoAbstractFactory = new DAOAbstractFactoryImpl();
        recipeDAO = daoAbstractFactory.getRecipeDAO(connectionToDb);
    }

    public long addRecipe(String description, long patientID, long doctorID,
                          GregorianCalendar dateOfCreate, int validity, String priority) throws AddDataException {
        try {
            validate(description,patientID,doctorID,dateOfCreate,validity,priority);
            return recipeDAO.create(new Recipe(FAKE_ID,description,patientID,doctorID,dateOfCreate,
                    validity,priority));
        } catch (ExecuteSQLException | DataException e) {
            throw new AddDataException(e.getMessage());
        }
    }

    public void changeRecipe(String description, long patientID, long doctorID,
                             GregorianCalendar dateOfCreate, int validity, String priority) throws ChangeDataException {
        try {
            validate(description,patientID,doctorID,dateOfCreate,validity,priority);
            recipeDAO.update(new Recipe(FAKE_ID,description,patientID,doctorID,dateOfCreate,
                    validity,priority));
        } catch (ExecuteSQLException | DataException e) {
            throw new ChangeDataException(e.getMessage());
        }
    }

    public  void deleteRecipe(Recipe recipe) throws DeleteDataException {
        try{
            recipeDAO.delete(recipe);
        } catch (ExecuteSQLException e) {
            throw new DeleteDataException(e.getMessage());
        }
    }

    public Set<Recipe> getRecipes() throws DataException {
        try{
            return recipeDAO.readAll();
        } catch (ExecuteSQLException e) {
            throw  new DataException(e.getMessage());
        }
    }

    private void validate(String description, long patientID, long doctorID,
                          GregorianCalendar dateOfCreate, int validity, String priority) throws DataException {
        validateString(description,DESCRIPTION_LIMIT,"ОПИСАНИЕ");
        if(patientID==0){
            throw new DataException("ПАЦИЕНТ не выбран");
        }
        if(doctorID==0){
            throw  new DataException("ДОКТОР не выбран");
        }
        Calendar today = GregorianCalendar.getInstance();
        if(dateOfCreate.get(Calendar.YEAR)>today.get(Calendar.YEAR)){
            throw  new DataException("Выбранная дата больше текущей");
        }else if(dateOfCreate.get(Calendar.YEAR)==today.get(Calendar.YEAR)){
            if(dateOfCreate.get(Calendar.MONTH)>today.get(Calendar.MONTH)){
                throw  new DataException("Выбранная дата больше текущей");
            }else if(dateOfCreate.get(Calendar.MONTH)==today.get(Calendar.MONTH)){
                if(dateOfCreate.get(Calendar.DAY_OF_MONTH)<=today.get(Calendar.DAY_OF_MONTH)){
                    throw  new DataException("Выбранная дата больше текущей");
                }
            }
        }
        if(validity==0){
            throw  new DataException("Строка СРОК ДЕЙСТВИЯ пуста");
        }
        validateString(priority,PRIORITY_LIMIT,"ПРИОРИТЕТ");
    }
}
