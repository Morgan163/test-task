package com.haulmont.testtask.DAO.DAOImpl;

import com.haulmont.testtask.DAO.DAOAbstractFactory;
import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.DB.ConnectionToDb;

/**
 * Created by andrei on 09.07.17.
 */
public class DAOAbstractFactoryImpl implements DAOAbstractFactory {
    @Override
    public DoctorDAO getDoctorDAO(ConnectionToDb connectionToDb) {
        return new DoctorDAOImpl(connectionToDb);
    }

    @Override
    public PatientDAO getPatientDAO(ConnectionToDb connectionToDb) {
        return new PatientDAOImpl(connectionToDb);
    }

    @Override
    public RecipeDAO getRecipeDAO(ConnectionToDb connectionToDb) {
        return new RecipeDAOImpl(connectionToDb);
    }
}
