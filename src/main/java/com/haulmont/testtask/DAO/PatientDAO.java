package com.haulmont.testtask.DAO;

import com.haulmont.testtask.Exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;

import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public interface PatientDAO {
    public void create(Patient patient) throws ExecuteSQLException;
    public void update(Patient patient) throws ExecuteSQLException;
    public void delete(Patient patient) throws ExecuteSQLException;
    public Patient read(int id) throws ExecuteSQLException;
    public Set<Patient> readAll() throws ExecuteSQLException;
}
