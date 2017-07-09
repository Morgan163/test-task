package com.haulmont.testtask.DAO.DAOImpl;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;

/**
 * Created by andrei on 09.07.17.
 */
public class DAOAbstractFactoryImpl implements DAOAbstractFactory {
    @Override
    public DoctorDAO getDoctorDAO() {
        return new DoctorDAOImpl();
    }

    @Override
    public PatientDAO getPatientDAO() {
        return new PatientDAOImpl();
    }

    @Override
    public RecipeDAO getRecipeDAO() {
        return new RecipeDAOImpl();
    }
}
