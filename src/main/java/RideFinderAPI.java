import static spark.Spark.get;
import static spark.Spark.notFound;

import java.util.HashMap;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;

import Lib.Supplier;
import Lib.SupplierNetwork;
import Lib.SupplierResult;
import Lib.Trip;

public class RideFinderAPI {
    
    private static SupplierNetwork suppliers = new SupplierNetwork();;

    // Define API route /rides 
    public static void main(String[] args) {
        addDefaultSuppliers();
        addDefaultVehicles();

        get("/rides", (req, res) -> {
            res.type("application/json");

            // Get query params.
            String pickup = req.queryParams("pickup");
            String dropoff = req.queryParams("dropoff");
            String passengers = req.queryParams("passengers");
            
            // Validate.
            if(!validInputs(pickup, dropoff, passengers)) {
                res.status(400);
                return "{\"message\":\"pickup, dropoff and passengers query params are required.\"}";
            }

            // Query suppliers.
            try {
                Trip trip = new Trip(pickup, dropoff, Integer.parseInt(passengers));
                return resultMapToJsonObj(suppliers.querySuppliers(trip));
            } catch(NumberFormatException e) {
                res.status(400);
                return "{\"message\":\"Number of passengers must be a number.\"}";
            } catch(UnirestException e) {
                res.status(400);
                return new JSONObject("{\"message\":\""+e.getMessage()+"\"}");
            }
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Route not found.\"}";
        });
    }

    // Convert a result map to a Json array of Json objects
    private static JSONArray resultMapToJsonObj(HashMap<String, SupplierResult> map) {
        JSONArray jsonResults = new JSONArray();
        map.keySet().forEach(item -> {
            String car = (String) item;
            String name = map.get(car).getSupplier().getSupplierId();
            int price = map.get(car).getPrice();
            String s = String.format("{car_type: %s, supplier: %s, price: %s}", car, name, price);
            JSONObject offer = new JSONObject(s);
            jsonResults.put(offer);
        });
        return jsonResults;
    }

    private static boolean validInputs(String pickup, String dropoff, String passengers) {
        if(pickup == null || dropoff == null || passengers == null) return false;
        return true;
    }

    private static void addDefaultVehicles() {
        Supplier.addCarType("STANDARD", 4);
        Supplier.addCarType("EXECUTIVE", 4);
        Supplier.addCarType("LUXURY", 4);
        Supplier.addCarType("PEOPLE_CARRIER", 6);
        Supplier.addCarType("LUXURY_PEOPLE_CARRIER", 6);
        Supplier.addCarType("MINIBUS", 16);
    }

    private static void addDefaultSuppliers() {
        suppliers.addSupplier(new Supplier("Dave", "https://techtest.rideways.com/dave"));
        suppliers.addSupplier(new Supplier("Eric", "https://techtest.rideways.com/eric"));
        suppliers.addSupplier(new Supplier("Jeff", "https://techtest.rideways.com/jeff"));
    }
}
