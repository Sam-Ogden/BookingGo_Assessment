package Lib;

/**
 * Manages trip details 
 */
public class Trip {
    private String pickupLocation;
    private String dropoffLocation;
    private int numPassengers = 0; 

    public Trip(String _pickup, String _dropOff) {
        pickupLocation = _pickup;
        dropoffLocation = _dropOff;
    }

    public Trip(String _pickup, String _dropOff, int _numPassengers) {
        this(_pickup, _dropOff);
        numPassengers = _numPassengers;
    }

    public String getPickup() {
        return pickupLocation;
    }

    public String getDropoff() {
        return dropoffLocation;
    }

    public int getNumPassengers() {
        return numPassengers;
    }
}