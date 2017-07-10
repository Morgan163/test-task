package com.haulmont.testtask.model;

/**
 * Created by andrei on 09.07.17.
 */
public class Doctor {

    private long id;
    private String name;
    private String surname;
    private String secondName;
    private String specialization;

    public Doctor(long id, String name, String surname, String secondName, String specialization) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.specialization = specialization;
    }


    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (name != null ? !name.equals(doctor.name) : doctor.name != null) return false;
        if (surname != null ? !surname.equals(doctor.surname) : doctor.surname != null) return false;
        if (secondName != null ? !secondName.equals(doctor.secondName) : doctor.secondName != null) return false;
        return specialization != null ? specialization.equals(doctor.specialization) : doctor.specialization == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        return result;
    }
}
