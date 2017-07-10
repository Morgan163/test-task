package com.haulmont.testtask.DB;

import java.io.*;
import java.sql.*;


/**
 * Created by андрей on 04.07.2017.
 */
public class ConnectionToDb {
    private Connection connection;


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
            connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
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

    public void execute(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ResultSet executeSQL(String sql) throws SQLException {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return  resultSet;
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
    public static void main(String[] args) throws SQLException {
        ConnectionToDb db = new ConnectionToDb();
        if (db.loadDriver()) {
            Connection connection = db.getConnection();
            try {
                db.executeSQL("SELECT * FROM RECIPES");
            }catch (SQLException e){
                db.execute(db.getSqlFromFile("src/main/resources/sql/createPatientTable.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/createDoctorTable.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/createRecipeTable.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/alterTableForRecipe.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/patientSequence.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/doctorSequence.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/recipeSequence.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/insertIntoTables.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/patientRemoveTrigger.sql"));
                db.execute(db.getSqlFromFile("src/main/resources/sql/doctorRemoveTrigger.sql"));
            }
            db.closeConnection(connection);
        }

    }
}
