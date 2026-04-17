package edu.upc.dsa;

import edu.upc.dsa.exceptions.FlightNotFoundException;
import edu.upc.dsa.models.Flight;
import edu.upc.dsa.FlightManagerImpl;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.Luggage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class FlightManagerTest {
    FlightManager fm;

    @Before
    public void setUp() {
        this.fm = FlightManagerImpl.getInstance();
       try {
           //afegim avions
           this.fm.addPlane("P1", "Boeing 737", "Iberia");
           this.fm.addPlane("P2", "Airbus A320", "Vueling");
           this.fm.addPlane("P3", "Boeing 777", "Air Europa");
           //afegim vols
           this.fm.addFlight("F2", "2025-12-20T19:34:00", "2025-12-20T20:55:00", "P1", "Madrid", "Barcelona");
           this.fm.addFlight("F1", "2025-12-20T07:30:00", "2025-12-20T10:35:00", "P2", "Barcelona", "Zurich");
           this.fm.addFlight("F3", "2025-12-20T12:03:00", "2025-12-20T14:35:00", "P3", "Paris", "Barcelona");
       }
       catch(FlightNotFoundException e)
       {

       }

    }
    @After
    public void tearDown() {
        // És un Singleton
        this.fm.clear();
    }
   @Test
   public void testAddPlane(){
    
    Assert.assertEquals(6, fm.size());
    try {
           this.fm.addPlane("P4", "Boeing 747", "Iberia");
    }
    catch (FlightNotFoundException e)
    {

    }

    Assert.assertEquals(7, fm.size());

   }
    @Test
    public void testGetPlane()throws FlightNotFoundException {
        Assert.assertEquals(6, fm.size());

        Plane p = this.fm.getPlane("P2");
        Assert.assertEquals("Airbus A320", p.getModel());
        Assert.assertEquals("Vueling", p.getCompany());

        Assert.assertThrows(FlightNotFoundException.class, () ->
                this.fm.getPlane("XXXXXXX"));

    }

    @Test
    public void testAddFlight() throws FlightNotFoundException {
        Assert.assertEquals(6, fm.size());

        this.fm.addFlight("F4", "2025-12-21T08:00:00", "2025-12-21T10:00:00", "P1", "Madrid", "Valencia");

        Assert.assertEquals(7, fm.size());
    }

    @Test(expected = FlightNotFoundException.class)
    public void testAddFlightWithNonExistentPlane() throws FlightNotFoundException {
        // si intentem afegir un vol amb avió que NO existeix que salti una excepcio
        Flight flight = this.fm.addFlight("F5", "14:00", "16:00", "P8", "Barcelona", "Paris");
    }

    @Test
    public void testCheckInLuggage() throws FlightNotFoundException {
        String luggageId = this.fm.checkInLuggage("user1", "F2");
        
        Assert.assertNotNull(luggageId);
        Assert.assertFalse(luggageId.isEmpty());
        
       
        Assert.assertEquals(7, fm.size());
    }

    @Test
    public void testGetLuggageByFlight() throws FlightNotFoundException {
        // facturem 3 maletes
        String luggage1 = this.fm.checkInLuggage("Carla", "F1");
        String luggage2 = this.fm.checkInLuggage("Marta", "F1");
        String luggage3 = this.fm.checkInLuggage("Luisa", "F1");
        
        List<Luggage> luggages = this.fm.getLuggageByFlight("F1");
        
        Assert.assertNotNull(luggages);
        Assert.assertEquals(3, luggages.size());
        
        Assert.assertEquals("Luisa", luggages.get(0).getUserId());
        Assert.assertEquals("Marta", luggages.get(1).getUserId());
        Assert.assertEquals("Carla", luggages.get(2).getUserId());
    }
    @Test
    public void testClear() throws FlightNotFoundException {
        // afegir una maleta
        this.fm.checkInLuggage("Lorena", "F2");
        
        // verificar que tenim dades
        Assert.assertTrue(fm.size() > 0);
        
        // clear
        this.fm.clear();
        
        // verificar que esta buit
        Assert.assertEquals(0, fm.size());
    }
}
