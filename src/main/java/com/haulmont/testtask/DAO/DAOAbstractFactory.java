package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DB.ConnectionToDb;

/**
 * Created by andrei on 09.07.17.
 */
public interface DAOAbstractFactory {
    public DoctorDAO getDoctorDAO(ConnectionToDb connectionToDb);
    public PatientDAO getPatientDAO(ConnectionToDb connectionToDb);
    public RecipeDAO getRecipeDAO(ConnectionToDb connectionToDb);

}
