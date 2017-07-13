package com.haulmont.testtask.controllers;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DAOImpl.DAOAbstractFactoryImpl;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.database.ConnectionToDb;
import com.haulmont.testtask.exceptions.*;
import com.haulmont.testtask.model.Patient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anlu0816 on 7/10/2017.
 */
public class PatientsController extends AbstractController {

    private ConnectionToDb connectionToDb;
    private DAOAbstractFactory daoAbstractFactory;
    private PatientDAO patientDAO;

    private Map<Long,Patient> patientMap;

    public PatientsController(ConnectionToDb connectionToDb) {
        this.connectionToDb = connectionToDb;
        daoAbstractFactory = new DAOAbstractFactoryImpl();
        patientDAO = daoAbstractFactory.getPatientDAO(connectionToDb);
    }

    public long addPatient(String name, String surname, String secondName, String phoneNumber) throws AddDataException {
        try {

            return patientDAO.create(validate(name,surname,secondName,phoneNumber));
        } catch (ExecuteSQLException | DataException e) {
            throw new AddDataException(e.getMessage());
        }
    }

    public void changePatient(long id,String name, String surname, String secondName, String phoneNumber) throws ChangeDataException {
        try {
            Patient patient = validate(name,surname,secondName,phoneNumber);
            patient.setId(id);
            patientDAO.update(patient);
        } catch (ExecuteSQLException | DataException e) {
            throw new ChangeDataException(e.getMessage());
        }
    }

    public void deletePatient(Patient patient) throws DeleteDataException {
        try{
            patientDAO.delete(patient);
        }catch (ExecuteSQLException e){
            throw new DeleteDataException(e.getMessage());
        }
    }

    public Set<Patient> getPatients() throws DataException {
        try{
            if(patientMap == null){
                patientMap = new HashMap<>();
            }
            Set<Patient> patients = patientDAO.readAll();
            for(Patient patient:patients){
                patientMap.put(patient.getId(),patient);
            }
            return patients;
        }catch (ExecuteSQLException e){
            throw new DataException(e.getMessage());
        }
    }

    public Patient getPatientById(long id) throws DataException {
        if(patientMap==null){
            getPatients();
        }
        return patientMap.get(id);
    }

    private Patient validate(String name, String surname, String secondName, String phoneNumber) throws DataException {
        validateString(name,NAME_LIMIT,"ИМЯ");
        validateString(surname,SURNAME_LIMIT,"ФАМИЛИЯ");
        validateString(secondName,SECOND_NAME_LIMIT,"ОТЧЕСТВО");
        if(phoneNumber.matches(".*\\D.*")){
            throw new DataException("Строка НОМЕР ТЕЛЕФОНА должна состоять из цифр");
        }
        long phone = Long.parseLong(phoneNumber);
        if(phone==0){
            throw new DataException("Строка НОМЕР ТЕЛЕФОНА пустая");
        }
        return new Patient(FAKE_ID,name, surname, secondName, phone);
    }
}
