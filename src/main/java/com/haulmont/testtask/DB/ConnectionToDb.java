package com.haulmont.testtask.DB;

import java.io.*;
import java.sql.*;


/**
 * Created by андрей on 04.07.2017.
 */
public class ConnectionToDb {

    public static void main(String[] args) {
        ConnectionToDb db = new ConnectionToDb();
        if (db.loadDriver()) {
            Connection connection = db.getConnection();
            if (!db.executeSQL(connection,"SELECT * FROM RECIPES")) {
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/createPatientTable.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/createDoctorTable.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/createRecipeTable.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/alterTableForRecipe.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/patientSequence.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/doctorSequence.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/recipeSequence.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/insertIntoTables.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/patientRemoveTrigger.sql"));
                db.execute(connection, db.getSqlFromFile("src/main/resources/sql/doctorRemoveTrigger.sql"));
            }
            db.executeSQL(connection,"DELETE FROM DOCTORS WHERE id = 2");
            db.closeConnection(connection);
        }

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

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

    public void execute(Connection connection, String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean executeSQL(Connection connection, String sql) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.println(resultSet.getLong(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));
                System.out.println(resultSet.getLong(5));
            }
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return  false;
        }
        return true;
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
}
