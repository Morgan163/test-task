package com.haulmont.testtask.DAO;

import com.haulmont.testtask.exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Patient;

import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public interface PatientDAO {
    public long create(Patient patient) throws ExecuteSQLException;
    public void update(Patient patient) throws ExecuteSQLException;
    public void delete(Patient patient) throws ExecuteSQLException;
    public Patient read(long id) throws ExecuteSQLException;
    public Set<Patient> readAll() throws ExecuteSQLException;
}
