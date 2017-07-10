package com.haulmont.testtask.model;

import java.util.GregorianCalendar;

/**
 * Created by andrei on 09.07.17.
 */
public class Recipe {
    private long id;
    private String description;
    private long patientID;
    private long doctorID;
    private GregorianCalendar dateOfCreate;
    private int validity;
    private String priority;


    public Recipe(long id, String description, long patientID, long doctorID, GregorianCalendar dateOfCreate, int validity, String priority) {

        this.id = id;
        this.description = description;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateOfCreate = dateOfCreate;
        this.validity = validity;
        this.priority = priority;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(long doctorID) {
        this.doctorID = doctorID;
    }

    public GregorianCalendar getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(GregorianCalendar dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (patientID != recipe.patientID) return false;
        if (doctorID != recipe.doctorID) return false;
        if (validity != recipe.validity) return false;
        if (!description.equals(recipe.description)) return false;
        if (!dateOfCreate.equals(recipe.dateOfCreate)) return false;
        return priority.equals(recipe.priority);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (int) (patientID ^ (patientID >>> 32));
        result = 31 * result + (int) (doctorID ^ (doctorID >>> 32));
        result = 31 * result + dateOfCreate.hashCode();
        result = 31 * result + validity;
        result = 31 * result + priority.hashCode();
        return result;
    }
}
