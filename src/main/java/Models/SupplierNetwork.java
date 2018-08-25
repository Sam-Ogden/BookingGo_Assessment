package Models;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * Handles the taxi firm network and querying taxi firms.
 */
public class SupplierNetwork {

    private LinkedList<Supplier> suppliers;

    public SupplierNetwork() {
        suppliers = new LinkedList<Supplier>();
    }

    public String querySuppliers(Trip t) {
        Iterator<Supplier> it = suppliers.iterator();
        while(it.hasNext()) {
            Supplier currSupplier = it.next();
            String supplierResults = currSupplier.querySupplier(t);

            // Process results 

        }
        return "";
    }

    public void addSupplier(Supplier s) {
        suppliers.add(s);
    }
}