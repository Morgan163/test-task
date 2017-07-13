package com.haulmont.testtask.windows;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.UI.forms.RecipeForm;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.ui.Window;

/**
 * Created by anlu0816 on 7/13/2017.
 */
public class RecipeWindow extends Window {

    public RecipeWindow(MainUI mainUI, Recipe recipe, String action) {

        RecipeForm recipeForm = new RecipeForm(mainUI,recipe,this, action);
        setContent(recipeForm);
    }
}
