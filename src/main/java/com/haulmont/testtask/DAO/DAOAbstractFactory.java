package com.haulmont.testtask.DAO;

/**
 * Created by andrei on 09.07.17.
 */
public interface DAOAbstractFactory {
    public DoctorDAO getDoctorDAO();
    public PatientDAO getPatientDAO();
    public RecipeDAO getRecipeDAO();

}
