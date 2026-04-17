package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Flight {

    String id;
    String timeleaving;
    String timearrival;
    String planeassigned;
    String destination;
    String origin;

    static int lastId;

    public Flight() {
        this.setId(RandomUtils.getId());
    }
    public Flight(String timeleaving, String timearrival, String planeassigned, String destination, String origin) {
        this(null, timeleaving, timearrival, planeassigned, destination, origin);
    }

    public Flight(String id, String timeleaving, String timearrival, String planeassigned, String destination, String origin) {
        this();
        if (id != null) this.setId(id);
        this.setTimeleaving(timeleaving);
        this.setTimearrival(timearrival);
        this.setPlaneassigned(planeassigned);
        this.setDestination(destination);
        this.setOrigin(origin);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getTimeleaving() {
        return timeleaving;
    }

    public void setTimeleaving(String timeleaving) {
        this.timeleaving = timeleaving;
    }

    public String getTimearrival() {
        return timearrival;
    }

    public void setTimearrival(String timearrival) {
        this.timearrival = timearrival;
    }

    public String getPlaneassigned() {
        return planeassigned;
    }

    public void setPlaneassigned(String planeassigned) {
        this.planeassigned = planeassigned;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }   

    @Override
    public String toString() {
        return "Flight [id=" + id + ", timeleaving=" + timeleaving + ", timearrival=" + timearrival + ", planeassigned=" + planeassigned + ", destination=" + destination + ", origin=" + origin + "]";
    }

}