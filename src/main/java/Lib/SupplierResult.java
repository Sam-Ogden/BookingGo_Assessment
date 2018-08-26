package Lib;

public class SupplierResult {
    private Supplier supplier;
    private int price;

    public SupplierResult(Supplier _supplier, int _price) {
        supplier = _supplier;
        price = _price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getPrice() {
        return price;
    }
}