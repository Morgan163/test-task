package com.haulmont.testtask.model;

import java.util.GregorianCalendar;

/**
 * Created by andrei on 09.07.17.
 */
public class Recipe {
    private Long id;
    private String description;
    private Long patientID;
    private Long doctorID;
    private GregorianCalendar dateOfCreate;
    private int validity;
    private String priority;


    public Recipe(Long id, String description, Long patientID, Long doctorID, GregorianCalendar dateOfCreate, int validity, String priority) {

        this.id = id;
        this.description = description;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateOfCreate = dateOfCreate;
        this.validity = validity;
        this.priority = priority;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
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

        if (validity != recipe.validity) return false;
        if (!description.equals(recipe.description)) return false;
        if (!patientID.equals(recipe.patientID)) return false;
        if (!doctorID.equals(recipe.doctorID)) return false;
        if (!dateOfCreate.equals(recipe.dateOfCreate)) return false;
        return priority.equals(recipe.priority);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + patientID.hashCode();
        result = 31 * result + doctorID.hashCode();
        result = 31 * result + dateOfCreate.hashCode();
        result = 31 * result + validity;
        result = 31 * result + priority.hashCode();
        return result;
    }

}
