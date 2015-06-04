package test;


import generated.Customer;
import generated.ISalesAggregationService;
import generated.Sale;
import generated.SaleItem;
import generated.SalesAggregation;
import java.util.Collection;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTestRPC {
    
    private SaleItem saleItem;
    private Customer cust;
    private Collection<SaleItem> saleCollection;
    private String date = "01-01-2015";
    private Sale sale = new Sale();
    private ISalesAggregationService salesAggService;
    
    public JUnitTestRPC() {
    }
    
    @Before
    public void setUp() {
        saleCollection = new HashSet();
        SalesAggregation anonSales = new SalesAggregation();
        salesAggService = anonSales.getSalesAggregationPort(); 
        newCustomer("M", "01-01-1990");
        newSaleItem("Item one", 99.9, 49.99);
        addSaleCollection(saleItem);
        createSale(date, saleCollection, cust);
    }
    
    @After
    public void tearDown() {
    }

    public void newSaleItem(String id, Double quantity, Double price) {
        this.saleItem = new SaleItem();
        this.saleItem.setProductId(id);
        this.saleItem.setQuantity(quantity);
        this.saleItem.setPrice(price);
    }
    
    public void newCustomer(String gender, String dateOfBirth) {
        this.cust = new Customer();
        this.cust.setGender(gender);
        this.cust.setDateOfBirth(dateOfBirth);
    }
    
    public Collection<SaleItem> addSaleCollection(SaleItem i) {
        saleCollection.add(i);
        return saleCollection;
    }
    
    public Sale createSale(String date, Collection<SaleItem> saleItems, Customer cust) {
        this.sale.setDate(date);
        this.sale.getSaleItems().addAll(saleItems);
        this.sale.setCust(cust);
        return this.sale;
    }
    
    @Test
    public void testSale() {
        salesAggService.newSale(sale);
        assertTrue(true);
    }
    
}
