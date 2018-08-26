import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import Lib.Supplier;
import Lib.Trip;

public class SupplierTests {

    private static final String VALID_SUPPLIER_ID = "Dave";
    private static final String VALID_SUPPLIER_API = "https://techtest.rideways.com/dave";
    private static final String VALID_PICKUP = "53.483959,-2.244644";
    private static final String INVALID_PICKUP = "100000,100000";
    private static final String VALID_DROPOFF = "53.4774,2.2309";
    private static final int VALID_NUM_PASSENGERS = 1;

    // Tests for querySupplier not included as depend on external API, slowing test speed.

    // Filter results tests.
    @Test public void filterResults_10Passengers_OnlyBigCabReturned() {
        // Arrange.
        Supplier supplier = new Supplier(VALID_SUPPLIER_ID, VALID_SUPPLIER_API);
        JSONArray offers = new JSONArray();
        Trip trip = new Trip(VALID_PICKUP, VALID_DROPOFF, 10);
        filterResultsTestSetup(offers);

        // Act.
        LinkedList<JSONObject> finalResults = supplier.filterResults(trip, offers);
        String carType = (String) finalResults.getFirst().get("car_type");

        // Assert.
        assertEquals("1 car type returned", 1, finalResults.size());
        assertEquals("Car type returned is BigCab", "BigCab", carType);
    }

    @Test public void filterResults_1Passenger_2CarTypesReturned() {
        // Arrange.
        Supplier supplier = new Supplier(VALID_SUPPLIER_ID, VALID_SUPPLIER_API);
        JSONArray offers = new JSONArray();
        Trip trip = new Trip(VALID_PICKUP, VALID_DROPOFF, 1);
        filterResultsTestSetup(offers);

        // Act.
        LinkedList<JSONObject> finalResults = supplier.filterResults(trip, offers);

        // Assert.
        assertEquals("2 car type returned", 2, finalResults.size());
    }
    @Test public void filterResults_NoOffers_EmptyListReturned() {
        // Arrange.
        Supplier supplier = new Supplier(VALID_SUPPLIER_ID, VALID_SUPPLIER_API);
        // Empty array.
        JSONArray offers = new JSONArray();
        Trip trip = new Trip(VALID_PICKUP, VALID_DROPOFF, 10);

        // Act.
        LinkedList<JSONObject> finalResults = supplier.filterResults(trip, offers);

        // Assert.
        assertTrue("1 car type returned", finalResults.size() == 0);
    }

    private void filterResultsTestSetup(JSONArray offers) {
        Supplier.addCarType("SmallCab", 2);
        Supplier.addCarType("BigCab", 10);
        JSONObject offer1 = new JSONObject("{\"price\":123,\"car_type\":\"SmallCab\"}");
        JSONObject offer2 = new JSONObject("{\"price\":123,\"car_type\":\"BigCab\"}");
        offers.put(offer1);
        offers.put(offer2);
    }
    // Add car type tests.
    @Test public void addCarType_TypeExists_ReturnsFalse() {
        boolean isValid = Supplier.addCarType("car1", 1);
        assertFalse("addCarType should return false", Supplier.addCarType("car1", 1));
    }

    @Test public void addCarType_MaxPassengersToSmall_ReturnsFalse() {
        assertFalse("addCarType should return false", Supplier.addCarType("car2", 0));
    }

    @Test public void addCarType_ValidCar_ReturnsTrue() {
        assertTrue("addCarType should return True with valid car", Supplier.addCarType("car2", 1));
    }
  
}