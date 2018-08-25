import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Models.Supplier;
import Models.Trip;

public class SupplierTests {

    private static final String VALID_SUPPLIER_ID = "Dave";
    private static final String VALID_SUPPLIER_API = "https://techtest.rideways.com/dave";
    private static final String VALID_PICKUP = "53.483959,-2.244644";
    private static final String VALID_DROPOFF = "53.4774,2.2309";
    private static final int VALID_NUM_PASSENGERS = 1;

    // @Test public void querySupplier_ValidTrip_ReturnsNonEmptyString() {
    //     // Arrange
    //     Supplier supplier = new Supplier(VALID_SUPPLIER_ID, VALID_SUPPLIER_API);
    //     Trip journey = new Trip(VALID_PICKUP, VALID_DROPOFF, VALID_NUM_PASSENGERS);

    //     // Act
    //     String queryResult = supplier.querySupplier(journey);
    //     assertTrue("Query result is not empty", !queryResult.isEmpty());   
    // }

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