
package rpc.dao;

import rpc.domain.Sale;
import java.util.HashMap;

public class SalesDAO {
    
    private static final HashMap<String, Sale> sales = new HashMap<>();
    
    public void addSale(Sale sale) {
        sales.put(sale.getId(), sale);
    }
    
}
