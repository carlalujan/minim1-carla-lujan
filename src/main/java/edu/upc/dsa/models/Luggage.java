package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Luggage {

    String id;
    String userId;
    String flightId;

    static int lastId;

    public Luggage() {
        this.setId(RandomUtils.getId());
    }

    public Luggage(String userId, String flightId) {
        this(null, userId, flightId);
    }

    public Luggage(String id, String userId, String flightId) {
        this();
        if (id != null) this.setId(id);
        this.setUserId(userId);
        this.setFlightId(flightId);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        return "Luggage [id=" + id + ", userId=" + userId + ", flightId=" + flightId + "]";
    }

}
