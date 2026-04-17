package edu.upc.dsa;

import edu.upc.dsa.exceptions.FlightNotFoundException;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.Flight;
import edu.upc.dsa.models.Luggage;

import java.util.List;

public interface FlightManager {

    // Plane operations
    public Plane addPlane(String id, String model, String company) throws FlightNotFoundException;
    public Plane getPlane(String id) throws FlightNotFoundException;
    
    // Flight operations
    public Flight addFlight(String id, String timeleaving, String timearrival, String planeassigned, String origin, String destination) throws FlightNotFoundException;
    public Flight getFlight(String id) throws FlightNotFoundException;
    
    // Luggage operations
    public String checkInLuggage(String userId, String flightId) throws FlightNotFoundException;
    public List<Luggage> getLuggageByFlight(String flightId) throws FlightNotFoundException;

    // metodes per a gestionar maletes per si els necessito
    // public Luggage getLuggage(String id) throws TrackNotFoundException;
    // public Luggage addLuggage(String userId, String flightId) throws TrackNotFoundException;
    // public Luggage getLuggageByUserId(String userId) throws TrackNotFoundException;
    // public List<Luggage> findAllLuggages();

    public void clear();
    public int size();
}
