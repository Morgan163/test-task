package com.haulmont.testtask.DAO;

import com.haulmont.testtask.Exceptions.ExecuteSQLException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Recipe;

import java.util.Set;

/**
 * Created by andrei on 09.07.17.
 */
public interface RecipeDAO {
    public void create(Recipe recipe) throws ExecuteSQLException;
    public void update(Recipe recipe) throws ExecuteSQLException;
    public void delete(Recipe recipe) throws ExecuteSQLException;
    public Recipe read(int id) throws ExecuteSQLException;
    public Set<Recipe> readAll() throws ExecuteSQLException;
}
