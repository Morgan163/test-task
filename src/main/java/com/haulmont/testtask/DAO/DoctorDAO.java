package com.haulmont.testtask.DAO;

import com.haulmont.testtask.exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Doctor;

import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public interface DoctorDAO {

    public long create(Doctor doctor) throws ExecuteSQLException;
    public void update(Doctor doctor) throws ExecuteSQLException;
    public void delete(Doctor doctor) throws ExecuteSQLException;
    public Doctor read(int id) throws ExecuteSQLException;
    public Set<Doctor> readAll() throws ExecuteSQLException;
}
