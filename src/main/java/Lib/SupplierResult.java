package Lib;

/**
 * Sorted in descending price order.
 */
public class SupplierResult implements Comparable<SupplierResult> {
    private Supplier supplier;
    private String carType;
    private int price;
    

    public SupplierResult(Supplier _supplier, int _price, String _carType) {
        supplier = _supplier;
        price = _price;
        carType = _carType;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getPrice() {
        return price;
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public int compareTo(SupplierResult o) {
        return this.price - o.getPrice();
    }
}