package Lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Handles the taxi firm network and querying taxi firms.
 */
public class SupplierNetwork {

    private LinkedList<Supplier> suppliers;

    public SupplierNetwork() {
        suppliers = new LinkedList<Supplier>();
    }

    // Query all available suppliers and update offers for each vehicle type.
    public HashMap<String, SupplierResult> querySuppliers(Trip t) {
        
        HashMap<String, SupplierResult> bestOffers = new HashMap<String, SupplierResult>();
        
        // Loop through each available supplier.
        Iterator<Supplier> it = suppliers.iterator();        
        while(it.hasNext()) {
            Supplier currSupplier = it.next();
            HttpResponse<JsonNode> response = currSupplier.querySupplier(t);
            JSONObject result = new JSONObject();

            int status = response == null ? 0 : response.getStatus();
            if(status == 200) {
                result = response.getBody().getObject();
                // Filter result on passenger number.
                LinkedList<JSONObject> newOffers = currSupplier.filterResults(t, result.getJSONArray("options"));
                // Update best offers with new offers if has lower price.
                updateBestOffers(bestOffers, newOffers, currSupplier);
            } else {
                // Error with query or supplier API
                if(response != null) {
                    System.out.println(response.getBody().getObject().get("message"));
                }
            }
        }
        return bestOffers;
    }

    // Takes a map to update with new offers.
    public void updateBestOffers(HashMap<String, SupplierResult> bestOffers,
     LinkedList<JSONObject> newOffers, Supplier currSupplier) {
        newOffers.forEach(item -> {
            JSONObject opt = (JSONObject) item;
            String carType = opt.getString("car_type");
            int newOffer = opt.getInt("price");

            if(bestOffers.containsKey(carType)) {
                int currentOffer = bestOffers.get(carType).getPrice();
                if(newOffer < currentOffer) {
                    // Better offer received, update.
                    bestOffers.replace(carType, new SupplierResult(currSupplier, newOffer));
                }
            } else {
                // No previous offer for this car type 
                bestOffers.put(carType, new SupplierResult(currSupplier, newOffer));
            }
        });
    }

    public void addSupplier(Supplier s) {
        suppliers.add(s);
    }
}