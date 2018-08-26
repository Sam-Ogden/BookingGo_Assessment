package Lib;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Manages taxi firm details and API requests.
 */
public class Supplier {
    // Maintain list of possible vehicles from any supplier
    private static HashMap<String, Vehicle> cars = new HashMap<String, Vehicle>();
    private static final int CONNECTION_TIMEOUT = 2000;
    private static final int SOCKET_TIMEOUT = 2000;

    private String id;
    private String apiPath; 

    public Supplier(String _id, String _apiPath) {
        id = _id;
        apiPath = _apiPath;
    }

    // Returns filtered set of vehicles and their corresponding prices.
    public HttpResponse<JsonNode> querySupplier(Trip t) {
        String query = "pickup="+t.getPickup()+"&dropoff="+t.getDropoff();
        String url = apiPath+"?"+query;
        return getJsonResponse(url);
    }

    // Filter results based off number of passengers.
    public LinkedList<JSONObject> filterResults(Trip t, JSONArray options) {
        LinkedList<JSONObject> validOptions = new LinkedList<JSONObject>();
        int numPassengers = t.getNumPassengers();

        options.forEach(item -> {
            JSONObject option = (JSONObject) item;
            Vehicle car = cars.get(option.getString("car_type"));
            if(car != null) {
                if(car.getMaxPassengers() >= numPassengers) {
                    // Passengers can fit in car
                    validOptions.push(option);
                }
            }
        });

        return validOptions;
    }

    public String getSupplierId() {
        return id;
    }
    // Sends request that accepts Json response
    private HttpResponse<JsonNode> getJsonResponse(String url) {
        try {
            Unirest.setTimeouts(CONNECTION_TIMEOUT, SOCKET_TIMEOUT);
            HttpResponse<JsonNode> response = Unirest.get(url)
            .header("accept", "application/json")
            .asJson();
            return response;
        } catch(UnirestException e) {
            System.out.println(e.getMessage());
            return null;
        }
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

        public String getName() {
            return name;
        }

        public int getMaxPassengers() {
            return maxPassengers;
        }
    }
}