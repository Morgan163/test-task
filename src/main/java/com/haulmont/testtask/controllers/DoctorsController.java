package com.haulmont.testtask.controllers;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DAOImpl.DAOAbstractFactoryImpl;
import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.*;
import com.haulmont.testtask.model.Doctor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by anlu0816 on 7/10/2017.
 */
public class DoctorsController extends AbstractController {
    private static final int SPECIALIZATION_LIMIT = 30;

    private ConnectionToDb connectionToDb;
    private DAOAbstractFactory daoAbstractFactory;
    private DoctorDAO doctorDAO;

    private Map<Long,Doctor> doctorMap;

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
    public void changeDoctor(long id, String name, String surname, String secondName, String specialization) throws ChangeDataException {
        try {
            validate(name,surname,secondName,specialization);
            doctorDAO.update(new Doctor(id,name,surname,secondName,specialization));
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
            if(doctorMap==null){
                doctorMap = new HashMap<>();
            }
            Set<Doctor> doctors = doctorDAO.readAll();
            for (Doctor doctor:doctors){
                doctorMap.put(doctor.getId(),doctor);
            }
            return doctors;
        } catch (ExecuteSQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    public Map<Doctor,Integer> getStatistic() throws DataException {
        try{
            return doctorDAO.getStatistic();
        } catch (ExecuteSQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    public Doctor getDoctorById(long id) throws DataException {
        if(doctorMap==null){
            getDoctors();
        }
        return doctorMap.get(id);
    }

    

    private void validate(String name, String surname, String secondName, String specialization) throws DataException {
        validateString(name,NAME_LIMIT,"ИМЯ");
        validateString(surname,SURNAME_LIMIT,"ФАМИЛИЯ");
        validateString(secondName,SECOND_NAME_LIMIT,"ОТЧЕСТВО");
        validateString(specialization,SPECIALIZATION_LIMIT,"СПЕЦИАЛИЗАЦИЯ");
    }

}
