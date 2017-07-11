package com.haulmont.testtask.model;

import java.util.GregorianCalendar;

/**
 * Created by andrei on 09.07.17.
 */
public class Recipe {
    private long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private GregorianCalendar dateOfCreate;
    private int validity;
    private String priority;


    public Recipe(long id, String description, Patient patient, Doctor doctor, GregorianCalendar dateOfCreate, int validity, String priority) {

        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

        if (validity != recipe.validity) return false;
        if (!description.equals(recipe.description)) return false;
        if (!patient.equals(recipe.patient)) return false;
        if (!doctor.equals(recipe.doctor)) return false;
        if (!dateOfCreate.equals(recipe.dateOfCreate)) return false;
        return priority.equals(recipe.priority);

    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + patient.hashCode();
        result = 31 * result + doctor.hashCode();
        result = 31 * result + dateOfCreate.hashCode();
        result = 31 * result + validity;
        result = 31 * result + priority.hashCode();
        return result;
    }
}
