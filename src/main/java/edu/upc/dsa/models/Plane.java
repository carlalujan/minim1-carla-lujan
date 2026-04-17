package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Plane {

    String id;
    String model;
    String company;

    static int lastId;

    public Plane() {
        this.setId(RandomUtils.getId());
    }
    public Plane(String model, String company) {
        this(null, model, company);
    }

    public Plane(String id, String model, String company) {
        this();
        if (id != null) this.setId(id);
        this.setModel(model);
        this.setCompany(company);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Plane [id=" + id + ", model=" + model + ", company=" + company + "]";
    }

}