package com.haulmont.testtask.controllers;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DAOImpl.DAOAbstractFactoryImpl;
import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.*;
import com.haulmont.testtask.model.Doctor;

import java.util.Set;

/**
 * Created by anlu0816 on 7/10/2017.
 */
public class DoctorsController extends AbstractController {
    private static final int SPECIALIZATION_LIMIT = 30;

    private ConnectionToDb connectionToDb;
    private DAOAbstractFactory daoAbstractFactory;
    private DoctorDAO doctorDAO;

    public DoctorsController(ConnectionToDb connectionToDb) {
        this.connectionToDb = connectionToDb;
        daoAbstractFactory = new DAOAbstractFactoryImpl();
        doctorDAO = daoAbstractFactory.getDoctorDAO(connectionToDb);
    }

    public long addDoctor(String name, String surname, String secondName, String specialization) throws AddDataException {
        try {
            validate(name,surname,secondName,specialization);
            return doctorDAO.create(new Doctor(FAKE_ID,name,surname,secondName,specialization));
        } catch (ExecuteSQLException | DataException e) {
            throw new AddDataException(e.getMessage());
        }
    }
    public void changeDoctor(String name, String surname, String secondName, String specialization) throws ChangeDataException {
        try {
            validate(name,surname,secondName,specialization);
            doctorDAO.update(new Doctor(FAKE_ID,name,surname,secondName,specialization));
        } catch (ExecuteSQLException | DataException e) {
            throw new ChangeDataException(e.getMessage());
        }
    }

    public void deleteDoctor(Doctor doctor) throws DeleteDataException {
        try {
            doctorDAO.delete(doctor);
        } catch (ExecuteSQLException e) {
            throw new DeleteDataException(e.getMessage());
        }
    }

    public Set<Doctor> getDoctors() throws DataException {
        try {
            return doctorDAO.readAll();
        } catch (ExecuteSQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    

    private void validate(String name, String surname, String secondName, String specialization) throws DataException {
        validateString(name,NAME_LIMIT,"ИМЯ");
        validateString(surname,SURNAME_LIMIT,"ФАМИЛИЯ");
        validateString(secondName,SECOND_NAME_LIMIT,"ОТЧЕСТВО");
        validateString(specialization,SPECIALIZATION_LIMIT,"СПЕЦИАЛИЗАЦИЯ");
    }

}
