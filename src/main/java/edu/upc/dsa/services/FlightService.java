package edu.upc.dsa.services;

import edu.upc.dsa.FlightManager;
import edu.upc.dsa.FlightManagerImpl;
import edu.upc.dsa.exceptions.FlightNotFoundException;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.Luggage;
import edu.upc.dsa.models.Flight;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/flights", description = "Endpoint to Flight Service")
@Path("/flights")
public class FlightService {

    private FlightManager fm;
    final static Logger logger = Logger.getLogger(FlightService.class);

    public FlightService() {
        this.fm = FlightManagerImpl.getInstance();
        
        // Initialize sample data if empty
        if (fm.size() == 0) {
            logger.info("Initializing flights with sample data");

            try {
                // afegim avions
                this.fm.addPlane("P1", "Boeing 737", "Iberia");
                this.fm.addPlane("P2", "Airbus A320", "Vueling");
                this.fm.addPlane("P3", "Boeing 777", "Air Europa");

                // afegim vols
                this.fm.addFlight("F2", "2025-12-20T19:34:00", "2025-12-20T20:55:00", "P1", "Madrid", "Barcelona");
                this.fm.addFlight("F1", "2025-12-20T07:30:00", "2025-12-20T10:35:00", "P2", "Barcelona", "Zurich");
                this.fm.addFlight("F3", "2025-12-20T12:03:00", "2025-12-20T14:35:00", "P3", "Paris", "Barcelona");

                //afegir maletes
                this.fm.checkInLuggage("Carla", "F1");
                this.fm.checkInLuggage("Marta", "F1");
                this.fm.checkInLuggage("Luisa", "F1");
                this.fm.checkInLuggage("Marti", "F2");
                this.fm.checkInLuggage("Hector", "F2");
                this.fm.checkInLuggage("Elisabeth", "F2");
                this.fm.checkInLuggage("Ruben", "F3");
                this.fm.checkInLuggage("Jasmine", "F3");
                this.fm.checkInLuggage("Albert", "F23");
            }
            catch (FlightNotFoundException e) {
                logger.error("Error initializing sample data", e);
            }
        }
    }

   @POST
   @ApiOperation(value = "Add a new plane", notes = "Adds a new plane to the system")
   @ApiResponses(value = {
           @ApiResponse(code = 201, message = "Plane created successfully", response = Plane.class),
           @ApiResponse(code = 400, message = "Invalid input data")
   })
    @Path("/planes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlane (Plane plane){
        logger.info("POST /flights/planes");

        if(plane == null || plane.getId() == null || plane.getModel() == null || plane.getCompany() == null) {
            logger.warn("Invalid plane data: " + plane);
            return Response.status(400).entity("Invalid plane data").build();
        }

        try{
            Plane newplane = this.fm.addPlane(plane.getId(), plane.getModel(), plane.getCompany());
            return Response.status(201).entity(newplane).build();
        }
        catch (FlightNotFoundException e){
            return Response.status(500).entity("Unexpected error creating plane").build();
        }
    }

    @GET
    @ApiOperation(value = "Get a plane by ID", notes = "Return a plane by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Plane found", response = Plane.class),
            @ApiResponse(code = 404, message = "Plane not found")
    })
    @Path("/planes/{id}")    
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlane(@PathParam("id") String id) {
        logger.info("GET /flights/planes/" + id);

        try {
            Plane plane = this.fm.getPlane(id);
            return Response.status(200).entity(plane).build();
        }
        catch (FlightNotFoundException e) {
            logger.warn("Plane not found: " + id);
            return Response.status(404).entity("Plane not found").build();
        }
    }
    @POST
    @ApiOperation(value = "Add a new flight", notes = "Creates a new flight")
    @ApiResponses(value={
        @ApiResponse(code=201, message = "Flight created successfully", response = Flight.class),
        @ApiResponse(code=400, message = "Invalid input data"),
        @ApiResponse(code=404, message = "Assigned plane not found")

    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFlight(Flight flight) {
        logger.info("POST /flights");

        if (flight == null ||
                flight.getId() == null ||
                flight.getTimeleaving() == null ||
                flight.getTimearrival() == null ||
                flight.getPlaneassigned() == null ||
                flight.getOrigin() == null ||
                flight.getDestination() == null) {

            logger.warn("Invalid flight data");
            return Response.status(400).entity("Flight id, times, plane, origin and destination are required").build();
        }

        try {
            Flight newFlight = this.fm.addFlight(
                    flight.getId(),
                    flight.getTimeleaving(),
                    flight.getTimearrival(),
                    flight.getPlaneassigned(),
                    flight.getOrigin(),
                    flight.getDestination()
            );

            return Response.status(201).entity(newFlight).build();
        }
        catch (FlightNotFoundException e) {
            logger.warn("Assigned plane not found: " + flight.getPlaneassigned());
            return Response.status(404).entity("Assigned plane not found").build();
        }
    }
    @GET
    @ApiOperation(value = "Get a flight", notes = "Returns a flight by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Flight.class),
            @ApiResponse(code = 404, message = "Flight not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlight(@PathParam("id") String id) {
        logger.info("GET /flights/" + id);

        try {
            Flight flight = this.fm.getFlight(id);
            return Response.status(200).entity(flight).build();
        }
        catch (FlightNotFoundException e) {
            logger.warn("Flight not found: " + id);
            return Response.status(404).entity("Flight not found").build();
        }
    }
    @POST
    @ApiOperation(value = "Check in luggage", notes = "Adds luggage to a flight")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Luggage checked in successfully"),
            @ApiResponse(code = 400, message = "Invalid luggage data"),
            @ApiResponse(code = 404, message = "Flight not found")
    })
    @Path("/{flightId}/luggages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkInLuggage(@PathParam("flightId") String flightId, Luggage luggage) {
        logger.info("POST /flights/" + flightId + "/luggages");

        if (luggage == null || luggage.getUserId() == null) {
            logger.warn("Invalid luggage data");
            return Response.status(400).entity("UserId is required").build();
        }

        try {
            String luggageId = this.fm.checkInLuggage(luggage.getUserId(), flightId);
            return Response.status(201).entity(luggageId).build();
        }
        catch (FlightNotFoundException e) {
            logger.warn("Flight not found: " + flightId);
            return Response.status(404).entity("Flight not found").build();
        }
    }
    @GET
    @ApiOperation(value = "Get luggage by flight", notes = "Returns all luggage of a flight in unloading order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Luggage.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Flight not found")
    })
    @Path("/{flightId}/luggages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLuggagesByFlight(@PathParam("flightId") String flightId) {
        logger.info("GET /flights/" + flightId + "/luggages");

        try {
            List<Luggage> luggages = this.fm.getLuggageByFlight(flightId);
            GenericEntity<List<Luggage>> entity = new GenericEntity<List<Luggage>>(luggages) {};
            return Response.status(200).entity(entity).build();
        }
        catch (FlightNotFoundException e) {
            logger.warn("Flight not found: " + flightId);
            return Response.status(404).entity("Flight not found").build();
        }
    }
    @DELETE
    @ApiOperation(value = "Clear all data", notes = "Deletes all planes, flights and luggages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All data deleted")
    })
    @Path("/clear")
    @Produces(MediaType.TEXT_PLAIN)
    public Response clear() {
        logger.info("DELETE /flights/clear");
        this.fm.clear();
        return Response.status(200).entity("All data deleted").build();
    }
}