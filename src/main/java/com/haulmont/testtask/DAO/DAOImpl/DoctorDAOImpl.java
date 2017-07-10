package com.haulmont.testtask.DAO.DAOImpl;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public class DoctorDAOImpl implements DoctorDAO {
    private ConnectionToDb connection;

    public DoctorDAOImpl(ConnectionToDb connection){
        this.connection = connection;
    }

    @Override
    public void create(Doctor doctor) throws ExecuteSQLException {
        String sql = "INSERT INTO DOCTORS (id, name, surname, second_name, specialization)" +
                " VALUES (NEXT VALUE FOR doctorSequence," +
                " \'" +doctor.getName()+"\',"+
                " \'"+doctor.getSurname()+"\',"+
                " \'"+doctor.getSecondName()+"\',"+
                " \'"+doctor.getSpecialization()+"\';";
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при добавлении записи \n"+e.getMessage());
        }
    }

    @Override
    public void update(Doctor doctor) throws ExecuteSQLException {
        String sql = "UPDATE DOCTORS" +
                " SET name = "+doctor.getName()+
                " ,surname = "+doctor.getSurname()+
                " ,second_name = "+doctor.getSecondName()+
                " ,specialization = "+doctor.getSpecialization()+
                " WHERE id = "+doctor.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при изменении записи\n"+e.getMessage());
        }
    }

    @Override
    public void delete(Doctor doctor) throws ExecuteSQLException {
        String sql = "DELETE FROM DOCTORS" +
                "WHERE id = "+doctor.getId();
        try {
            connection.executeSQL(sql);
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при удалении записи\n"+e.getMessage());
        }
    }

    @Override
    public Doctor read(int id) throws ExecuteSQLException {
        Doctor doctor = null;
        String sql = "SELECT *" +
                " FROM DOCTORS" +
                " WHERE id = "+id;
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            doctor = new Doctor(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении доктора с id = "+id+" \n"+e.getMessage());
        }
        return doctor;
    }

    @Override
    public Set<Doctor> readAll() throws ExecuteSQLException {
        Set<Doctor> doctors = new HashSet<>();
        String sql = "SELECT *" +
                "FROM DOCTORS";
        ResultSet resultSet;
        try {
            resultSet = connection.executeSQL(sql);
            while (resultSet.next()){
                Doctor doctor = new Doctor(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new ExecuteSQLException("Ошибка при получении доктора списка докторов\n"+e.getMessage());
        }
        return doctors;
    }
}
