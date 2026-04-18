package edu.upc.dsa;

import edu.upc.dsa.exceptions.FlightNotFoundException;
import edu.upc.dsa.models.Flight;
import edu.upc.dsa.models.Luggage;
import edu.upc.dsa.models.Plane;

import java.util.LinkedList;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class FlightManagerImpl implements FlightManager {
    private static FlightManager instance;
    protected Map<String, Flight> flights;
    protected Map<String, Stack<Luggage>> luggagesByFlight;
    protected Map<String, Plane> planes;
    
    final static Logger logger = Logger.getLogger(FlightManagerImpl.class);

    private FlightManagerImpl() {
        this.flights = new HashMap<>();
        this.luggagesByFlight = new HashMap<>();
        this.planes = new HashMap<>();
        logger.info("FlightManager initialized");
    }

    public static FlightManager getInstance() {
        if (instance == null) {
            instance = new FlightManagerImpl();
        }
        return instance;
    }

    public int size() {
        int luggageCount = 0;
        for (Stack<Luggage> stack : this.luggagesByFlight.values()) {
            luggageCount += stack.size();
        }
        int ret = this.flights.size() + this.planes.size() + luggageCount;
        logger.info("Total items: flights=" + this.flights.size() + ", planes=" + this.planes.size() + ", luggages=" + luggageCount);
        return ret;
    }

    // ===== PLANE OPERATIONS =====
    @Override
    public Plane addPlane(String id, String model, String company) throws FlightNotFoundException {
        logger.info("Adding plane: id=" + id + ", model=" + model + ", company=" + company);
        
        Plane plane = new Plane(id, model, company);
        this.planes.put(id, plane);
        
        logger.info("Plane added/updated successfully: " + plane.getId());
        return plane;
    }

    @Override
    public Plane getPlane(String id) throws FlightNotFoundException {
        logger.info("Getting plane: id=" + id);
        
        Plane plane = this.planes.get(id);

        if(plane == null) throw new FlightNotFoundException();
        return plane;
        
    }
    //obtenir llista de avions
    @Override
    public List<Plane> findAllPlanes() {
        logger.info("Getting all planes");
        return new ArrayList<>(this.planes.values());
    }

// ===== FLIGHT OPERATIONS =====
    @Override
    public Flight addFlight(String id,String timeleaving, String timearrival, String planeassigned, String origin, String destination) throws FlightNotFoundException {
               logger.info("Adding flight id = " +id + ", origin = " + origin + ", destination = " + destination + ", timeleaving = " + timeleaving + ", timearrival = " + timearrival + ", planeassigned = " + planeassigned);
             
               //primer validem si el avio existeix, i despres el vol
               Plane plane = this.getPlane(planeassigned);

                Flight flight = new Flight(id, timeleaving, timearrival, planeassigned, origin, destination);
                this.flights.put(id, flight);//afegim / modifiquem el vol al Map

                //creem un stack per les maletes del vol
                if(!this.luggagesByFlight.containsKey(id)){
                    this.luggagesByFlight.put(id, new Stack<Luggage>());
                }
                logger.info("Flight added/updated successfully: " + flight.getId());
                return flight;

    }

    @Override
    public Flight getFlight(String id) throws FlightNotFoundException {
        logger.info("Getting flight: id=" + id);

        Flight flight = this.flights.get(id);

        if(flight == null) throw new FlightNotFoundException();
        logger.info("Flight found: " + id);
        return flight;
    }
    //obtenir llista de vols
    @Override
    public List<Flight> findAllFlights() {
        logger.info("Getting all flights");
        return new ArrayList<>(this.flights.values());
    }

    // ===== LUGGAGE OPERATIONS =====
    @Override
    public String checkInLuggage(String userId, String flightId) throws FlightNotFoundException {
        logger.info("Checking in luggage for user: userId=" + userId + ", flightId=" + flightId);
        
        //validem que el vol existeix
        Flight flight = this.getFlight(flightId);

        //creem una maleta nova
        Luggage luggage = new Luggage(userId, flightId);

        //Get el Stack de les maletes
        Stack<Luggage> stack = this.luggagesByFlight.get(flightId);

        //ara fiquem la maleta a la pila que sera la primera en descarregar
        stack.push(luggage);

        logger.info("Luggage checked in: " + luggage.getId() + " for flight: " + flightId);
        return luggage.getId();
    }

    @Override
    public List<Luggage> getLuggageByFlight(String flightId) throws FlightNotFoundException {
        logger.info("Getting luggages for flight id: " + flightId);

        //validem que el vol existeix
        Flight flight = this.getFlight(flightId);

        Stack<Luggage> stack = this.luggagesByFlight.get(flightId);
        //convertim en llista per a retornar en ordre de descarrega
        List<Luggage> luggages = new LinkedList<>();
        for (int i = stack.size() - 1; i >= 0; i--) {
        luggages.add(stack.get(i));
        }

        logger.info("Found " + luggages.size() + " luggages for flight: " + flightId);
        return luggages;
        
    }
    //obtenir totes les maletes
    @Override
    public List<Luggage> findAllLuggages() {
        logger.info("Getting all luggages");

        List<Luggage> allLuggages = new ArrayList<>();

        for (Stack<Luggage> stack : this.luggagesByFlight.values()) {
            allLuggages.addAll(stack);
        }

        return allLuggages;
    }


    @Override
    public void clear() {
        logger.info("Clearing all data.");
        this.flights.clear();
        this.planes.clear();
        this.luggagesByFlight.clear();
        logger.info("All data cleared.");
    }
}