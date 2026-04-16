package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Reader {

    String id;
    String name;
    String surname;
    String dni;
    String brithday;
    String placebirth;
    String address;

    static int lastId;

    public Reader() {
        this.setId(RandomUtils.getId());
    }

    public Reader(String name, String surname, String dni, String brithday, String placebirth, String address) {
        this(null, name, surname, dni, brithday, placebirth, address);
    }

    public Reader(String id, String name, String surname, String dni, String brithday, String placebirth, String address) {
        this();
        if (id != null) this.setId(id);
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.brithday = brithday;
        this.placebirth = placebirth;
        this.address = address;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getPlacebirth() {
        return placebirth;
    }

    public void setPlacebirth(String placebirth) {
        this.placebirth = placebirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Reader [id=" + id + ", name=" + name + ", surname=" + surname + ", dni=" + dni + ", brithday=" + brithday + ", placebirth=" + placebirth + ", address=" + address + "]";
    }
}
