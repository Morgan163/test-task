package com.haulmont.testtask.model;

/**
 * Created by andrei on 09.07.17.
 */
public class Patient {
    private long id;
    private String name;
    private String surname;
    private String secondName;
    private long phoneNumber;

    public Patient(long id, String name, String surname, String secondName, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmallName(){
        return surname+" "+name.substring(0,1)+". "+secondName.substring(0,1)+".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (phoneNumber != patient.phoneNumber) return false;
        if (!name.equals(patient.name)) return false;
        if (!surname.equals(patient.surname)) return false;
        return secondName.equals(patient.secondName);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
        return result;
    }
}
