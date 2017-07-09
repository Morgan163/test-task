package com.haulmont.testtask.model;

import java.util.Date;

/**
 * Created by andrei on 09.07.17.
 */
public class Recipe {
    private Long id;
    private Long patientID;
    private Long doctorID;
    private Date dateOfCreate;
    private int validity;
    private String priority;



    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
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
        if (!patientID.equals(recipe.patientID)) return false;
        if (doctorID != null ? !doctorID.equals(recipe.doctorID) : recipe.doctorID != null) return false;
        if (!dateOfCreate.equals(recipe.dateOfCreate)) return false;
        return priority.equals(recipe.priority);

    }

    @Override
    public int hashCode() {
        int result = patientID.hashCode();
        result = 31 * result + (doctorID != null ? doctorID.hashCode() : 0);
        result = 31 * result + dateOfCreate.hashCode();
        result = 31 * result + validity;
        result = 31 * result + priority.hashCode();
        return result;
    }
}
