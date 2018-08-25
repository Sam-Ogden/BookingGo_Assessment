import Models.Supplier;
import Models.SupplierNetwork;
import Models.Trip;

/*
 * Entry point for console app.
 */
public class RideFinder {
    
    private static SupplierNetwork suppliers = new SupplierNetwork();;

    public static void main(String[] args) {
        // Application set up
        addDefaultSuppliers();
        addDefaultVehicles();

        try {
            int numberPassengers = Integer.parseInt(args[2]);
            Trip trip = new Trip(args[0], args[1], numberPassengers);
            suppliers.querySuppliers(trip);
        } catch(NumberFormatException e) {
            System.out.println("Number of passengers must be a number.\n ");
            System.exit(1);
        }
    }

    public static void addDefaultVehicles() {
        Supplier.addCarType("STANDARD", 4);
        Supplier.addCarType("EXECUTIVE", 4);
        Supplier.addCarType("LUXURY", 4);
        Supplier.addCarType("PEOPLE_CARRIER", 6);
        Supplier.addCarType("LUXURY_PEOPLE_CARRIER", 6);
        Supplier.addCarType("MINIBUS", 16);
    }

    public static void addDefaultSuppliers() {
        suppliers.addSupplier(new Supplier("Dave", "https://techtest.rideways.com/dave"));
        suppliers.addSupplier(new Supplier("Eric", "https://techtest.rideways.com/eric"));
        suppliers.addSupplier(new Supplier("Jeff", "https://techtest.rideways.com/jeff"));
    }
}
