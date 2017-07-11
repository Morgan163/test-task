package com.haulmont.testtask.DAO.DAOImpl;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public class PatientDAOImpl implements PatientDAO {
    private ConnectionToDb connection;

    public PatientDAOImpl(ConnectionToDb connection) {
        this.connection = connection;
    }

    @Override
    public long create(Patient patient) throws ExecuteSQLException {
        String sql = "INSERT INTO PATIENTS(id, name, surname, second_name, phone_number)\n" +
                " VALUES (NEXT VALUE FOR patientSequence," +
                " \'" +patient.getName()+"\',"+
                " \'"+patient.getSurname()+"\',"+
                " \'"+patient.getSecondName()+"\',"+
                " "+patient.getPhoneNumber()+";";
        try {
            connection.executeSQL(sql);
            sql = "SELECT id" +
                    " FROM PATIENTS" +
                    " WHERE name = "+patient.getName()+
                    " AND surname = "+patient.getSurname()+
                    " AND second_name = "+patient.getSecondName()+
                    " AND phone_number = "+patient.getPhoneNumber();
            ResultSet resultSet = connection.executeSQL(sql);
            return  resultSet.getLong(1);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при добавлении записи \n"+e.getMessage());
        }
    }

    @Override
    public void update(Patient patient) throws ExecuteSQLException {
        String sql = "UPDATE PATIENTS" +
                " SET name = "+patient.getName()+
                " ,surname = "+patient.getSurname()+
                " ,second_name = "+patient.getSecondName()+
                " ,phone_number = "+patient.getPhoneNumber()+
                " WHERE id = "+patient.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при изменении записи\n"+e.getMessage());
        }
    }

    @Override
    public void delete(Patient patient) throws ExecuteSQLException {
        String sql = "DELETE FROM PATIENTS" +
                "WHERE id = "+patient.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при удалении записи\n"+e.getMessage());
        }
    }

    @Override
    public Patient read(long id) throws ExecuteSQLException {
        Patient patient = null;
        String sql = "SELECT *" +
                " FROM PATIENTS" +
                " WHERE id = "+id;
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            while (resultSet.next()) {
                patient = new Patient(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getLong(5));
            }
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении пациента с id = "+id+" \n"+e.getMessage());
        }
        return patient;
    }

    @Override
    public Set<Patient> readAll() throws ExecuteSQLException {
        Set<Patient> patients = new HashSet<>();
        Patient patient = null;
        String sql = "SELECT *" +
                " FROM PATIENTS";
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            while ((resultSet.next())) {
                patient = new Patient(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getLong(5));
                patients.add(patient);
            }
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении списка пациентов\n "+e.getMessage());
        }
        return patients;
    }

}
