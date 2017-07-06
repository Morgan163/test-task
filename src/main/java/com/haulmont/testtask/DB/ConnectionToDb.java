package com.haulmont.testtask.DB;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by андрей on 04.07.2017.
 */
public class ConnectionToDb {

    public static void main(String[] args) {
        ConnectionToDb db = new ConnectionToDb();
        if (db.loadDriver()) {
            Connection connection = db.getConnection();
            if (!db.executeSQL(connection,"SELECT * FROM PATIENTS")) {
                db.execute(connection, db.getSqlFromFile("src\\main\\resources\\createPatientTable.sql"));
                db.execute(connection, db.getSqlFromFile("src\\main\\resources\\createDoctorTable.sql"));
                db.execute(connection, db.getSqlFromFile("src\\main\\resources\\createRecipeTable.sql"));
                db.execute(connection, db.getSqlFromFile("src\\main\\resources\\alterTableForRecipe.sql"));
            }
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
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
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
