package com.haulmont.testtask.model;

/**
 * Created by andrei on 09.07.17.
 */
public class Patient {
    private long id;
    private String name;
    private String surname;
    private String secondName;
    private Long phoneNumber;

    public Patient(long id, String name, String surname, String secondName, Long phoneNumber) {
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (!name.equals(patient.name)) return false;
        if (!surname.equals(patient.surname)) return false;
        if (!secondName.equals(patient.secondName)) return false;
        return phoneNumber.equals(patient.phoneNumber);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        return result;
    }
}
