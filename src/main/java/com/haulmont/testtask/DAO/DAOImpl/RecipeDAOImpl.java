package com.haulmont.testtask.DAO.DAOImpl;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public class RecipeDAOImpl implements RecipeDAO {
    private ConnectionToDb connection;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;
    private DAOAbstractFactory daoAbstractFactory;

    public RecipeDAOImpl(ConnectionToDb connection) {
        this.connection = connection;
        daoAbstractFactory = new DAOAbstractFactoryImpl();
        doctorDAO = daoAbstractFactory.getDoctorDAO(connection);
        patientDAO = daoAbstractFactory.getPatientDAO(connection);
    }

    @Override
    public long create(Recipe recipe) throws ExecuteSQLException {
        String sql = "INSERT INTO RECIPES (id, description, patient_id, doctor_id, date_of_create, validity, priority)\n" +
                " VALUES (NEXT VALUE FOR recipeSequence," +
                " \'" +recipe.getDescription()+"\',"+
                " " +recipe.getPatient()+","+
                " "+recipe.getDoctor()+","+
                " to_date(\'"
                    +recipe.getDateOfCreate().get(Calendar.DAY_OF_MONTH)+ "/"
                    +recipe.getDateOfCreate().get(Calendar.MONTH)+"/"
                    +recipe.getDateOfCreate().get(Calendar.YEAR)+"\', 'DD/MM/YYYY'),"+
                " "+recipe.getValidity()+","+
                " \'"+recipe.getPriority()+"\';";
        try {
            connection.executeSQL(sql);
            sql = "SELECT id" +
                    " FROM RECIPES" +
                    " WHERE description = " +recipe.getDescription()+
                    " AND patient_id = " + recipe.getPatient()+
                    " AND doctor_id = "+recipe.getDoctor()+
                    " AND date_of_create = to_date(\'"
                    +recipe.getDateOfCreate().get(Calendar.DAY_OF_MONTH)+ "/"
                    +recipe.getDateOfCreate().get(Calendar.MONTH)+"/"
                    +recipe.getDateOfCreate().get(Calendar.YEAR)+"\', 'DD/MM/YYYY'),"+
                    " AND validity = "+recipe.getValidity()+
                    " AND priority = "+recipe.getPriority();
            ResultSet resultSet = connection.executeSQL(sql);
            return  resultSet.getLong(1);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при добавлении записи \n"+e.getMessage());
        }
    }

    @Override
    public void update(Recipe recipe) throws ExecuteSQLException {
        String sql = "UPDATE RECIPES" +
                " SET description = " +recipe.getDescription()+
                " ,patient_id = " + recipe.getPatient()+
                " ,doctor_id = "+recipe.getDoctor()+
                " ,date_of_create = to_date(\'"
                    +recipe.getDateOfCreate().get(Calendar.DAY_OF_MONTH)+ "/"
                    +recipe.getDateOfCreate().get(Calendar.MONTH)+"/"
                    +recipe.getDateOfCreate().get(Calendar.YEAR)+"\', 'DD/MM/YYYY'),"+
                " ,validity = "+recipe.getValidity()+
                " ,priority = "+recipe.getPriority()+
                " WHERE id = "+recipe.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при изменении записи\n"+e.getMessage());
        }
    }

    @Override
    public void delete(Recipe recipe) throws ExecuteSQLException {
        String sql = "DELETE FROM RECIPES" +
                "WHERE id = "+recipe.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при удалении записи\n"+e.getMessage());
        }
    }

    @Override
    public Recipe read(int id) throws ExecuteSQLException {
        Recipe recipe = null;
        String sql = "SELECT *" +
                " FROM RECIPES" +
                " WHERE id = "+id;
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            GregorianCalendar calendar = null;
            resultSet.getDate(5,calendar);
            recipe = new Recipe(resultSet.getLong(1),
                    resultSet.getString(2),
                    patientDAO.read(resultSet.getLong(3)),
                    doctorDAO.read(resultSet.getLong(4)),
                    calendar,
                    resultSet.getInt(6),
                    resultSet.getString(7));
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении рецепта с id = "+id+" \n"+e.getMessage());
        }
        return recipe;
    }

    @Override
    public Set<Recipe> readAll() throws ExecuteSQLException {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = null;
        String sql = "SELECT *" +
                " FROM RECIPES";
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            while(resultSet.next()) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(resultSet.getDate(5));
                recipe = new Recipe(resultSet.getLong(1),
                        resultSet.getString(2),
                        patientDAO.read(resultSet.getLong(3)),
                        doctorDAO.read(resultSet.getLong(4)),
                        calendar,
                        resultSet.getInt(6),
                        resultSet.getString(7));
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении списка рецептов \n"+e.getMessage());
        }
        return recipes;
    }
}
