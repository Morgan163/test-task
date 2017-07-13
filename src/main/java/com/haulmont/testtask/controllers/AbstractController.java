package com.haulmont.testtask.controllers;

import com.haulmont.testtask.exceptions.DataException;

/**
 * Created by anlu0816 on 7/10/2017.
 */
public abstract class AbstractController {
    public static final int NAME_LIMIT = 20;
    public static final int SURNAME_LIMIT = 30;
    public static final int SECOND_NAME_LIMIT = 30;
    public static final long  FAKE_ID= 0;



    protected void validateString(String str, int limit, String strName) throws DataException {
        if(str!=null){
                if ("".equals(str)) {
                    throw new DataException("Строка " + strName + " не введена");
                } else if (str.length() > limit) {
                    throw new DataException("Строка " + strName + " должна содержать <" + limit + " символов");
                } else if ((!strName.equals("ОПИСАНИЕ") && (!str.matches("\\D*")))) {
                    throw new DataException("Строка " + strName + " не должна содержать цифры");
                }
        }else{
            throw new DataException("Строка "+strName+" не введена");
        }
    }
}
