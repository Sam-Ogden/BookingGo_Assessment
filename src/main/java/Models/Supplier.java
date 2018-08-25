package Models;

import java.util.HashMap;
/**
 * Manages taxi firm details and API requests.
 */
public class Supplier {
    // Maintain list of possible vehicles from any supplier
    private static HashMap<String, Vehicle> cars = new HashMap<String, Vehicle>();

    private String id;
    private String apiPath; 

    public Supplier(String _id, String _apiPath) {
        id = _id;
        apiPath = _apiPath;
    }

    // Returns filtered set of vehicles and their corresponding prices.
    public String querySupplier(Trip t) {
        String query = "pickup="+t.getPickup()+"&dropoff="+t.getDropoff();
        String url = apiPath+"?"+query;
        return filterResults(t, getResponse(url));
    }

    // Filter results based off number of passengers.
    private String filterResults(Trip t, String queryResponse) {
        return "";
    }

    private String getResponse(String url) {
        return "";
   }

    /** 
     *  Allows additional cars to be added in future, e.g. Disabled access cars
     *  Returns false if attempting to add car type that already exists
     */
    public static boolean addCarType(String name, int maxPassengers) {
        if(maxPassengers < 1) return false;
        if(cars.containsKey(name)) return false;
        
        // Valid Car type
        cars.put(name, new Vehicle(name, maxPassengers));
        return true;
    }

    private static class Vehicle {
        // Stores vehicle details. 
        private String name;
        private int maxPassengers; 

        public Vehicle(String _name, int _maxPassengers) {
            name = _name;
            maxPassengers = _maxPassengers;
        }
    }
}