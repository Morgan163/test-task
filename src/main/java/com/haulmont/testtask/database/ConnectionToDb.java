package com.haulmont.testtask.database;

import com.haulmont.testtask.controllers.DoctorsController;
import com.haulmont.testtask.controllers.PatientsController;
import com.haulmont.testtask.controllers.RecipesController;
import com.haulmont.testtask.exceptions.DataException;

import java.io.*;
import java.sql.*;


/**
 * Created by андрей on 04.07.2017.
 */
public class ConnectionToDb {
    private Connection connection;

    public ConnectionToDb() throws SQLException {
        loadDriver();
        getConnection();
    }

    private boolean loadDriver() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
            return false;
        }
        return true;
    }

    public Connection getConnection() throws SQLException {
        connection = null;
        try {
            boolean b = new File("testdb.script").exists();
            connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");

            if(!b){
                initBd();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException();
        }
        return connection;
    }

    private String getSqlFromFile(String path) {
        String sql = "";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                sql += line;
            }
            return sql;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return sql;
    }

    public void execute(String sql) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public ResultSet executeSQL(String sql) throws SQLException {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public void closeConnection(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SHUTDOWN";
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void initBd() {
        if (loadDriver()) {
            try {
                Connection connection = getConnection();
                executeSQL("SELECT * FROM RECIPES");
            } catch (SQLException e) {
                try {
                    execute(getSqlFromFile("src/main/resources/sql/createPatientTable.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/createDoctorTable.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/createRecipeTable.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/alterTableForRecipe.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/patientSequence.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/doctorSequence.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/recipeSequence.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/insertIntoTables.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/patientRemoveTrigger.sql"));
                    execute(getSqlFromFile("src/main/resources/sql/doctorRemoveTrigger.sql"));
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());

                }
            }
        }
    }

    public static void main(String[] args) throws SQLException, DataException {
        ConnectionToDb db = new ConnectionToDb();
        RecipesController recipesController = new RecipesController(db);
        DoctorsController doctorsController = new DoctorsController(db);
        PatientsController patientsController = new PatientsController(db);
        //patientsController.changePatient(2,"Сергей","Корнеев","Сергеевич","89564736582");
        //doctorsController.changeDoctor(5,"Кирил","Лопаткин","Иванович","Терапевт");
        //recipesController.addRecipe("12",new Patient(1,"f","f","f",5555555),
        //        new Doctor(2,"d","d","d","d"),
        //        new Date(20495840),"3","Нормальный");
        //recipesController.getRecipesAfterFilter(1,"Нормальный","4",recipesController.getRecipes());
       /*

        }*/
       patientsController.getPatients();

    }
}
