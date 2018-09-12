import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONObject;
import org.junit.Test;

import Lib.Supplier;
import Lib.SupplierNetwork;
import Lib.SupplierResult;

public class SupplierNetworkTests {

    // Tests for querySuppliers not included as depend on external API and slows test speed.

    @Test public void updateBestOffers_ReceiveBetterOffer_MapUpdated() {
        // Arrange. 
        SupplierNetwork network = new SupplierNetwork();
        HashMap<String, SupplierResult> bestOffers = new HashMap<String, SupplierResult>();
        LinkedList<JSONObject> newOffers = new LinkedList<JSONObject>();
        Supplier oldSupplier = new Supplier("Dave", "https://example.com/dave");
        Supplier currentSupplier = new Supplier("Sam", "https://example.com/sam");
        SupplierResult oldOffer = new SupplierResult(oldSupplier, 2, "");
        
        bestOffers.put("CAR", oldOffer);
        newOffers.push(new JSONObject("{\"price\":1,\"car_type\":\"CAR\"}"));

        // Act. 
        network.updateBestOffers(bestOffers, newOffers, currentSupplier);

        // Assert.
        String actual = bestOffers.get("CAR").getSupplier().getSupplierId();
        assertEquals("Supplier for car type CAR should be 'Sam'", actual, "Sam");
    }

    @Test public void updateBestOffers_ReceiveWorseOffer_MapNotUpdated() {
        // Arrange. 
        SupplierNetwork network = new SupplierNetwork();
        HashMap<String, SupplierResult> bestOffers = new HashMap<String, SupplierResult>();
        LinkedList<JSONObject> newOffers = new LinkedList<JSONObject>();
        Supplier oldSupplier = new Supplier("Dave", "https://example.com/dave");
        Supplier currentSupplier = new Supplier("Sam", "https://example.com/sam");
        SupplierResult oldOffer = new SupplierResult(oldSupplier, 1, "");
        
        bestOffers.put("CAR", oldOffer);
        newOffers.push(new JSONObject("{\"price\":2,\"car_type\":\"CAR\"}"));

        // Act. 
        network.updateBestOffers(bestOffers, newOffers, currentSupplier);

        // Assert.
        String actual = bestOffers.get("CAR").getSupplier().getSupplierId();
        assertEquals("Supplier for car type CAR should be 'Dave'", actual, "Dave");
    }
}