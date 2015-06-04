
package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NewSale_QNAME = new QName("http://salesagg/", "newSale");
    private final static QName _NewSaleResponse_QNAME = new QName("http://salesagg/", "newSaleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NewSaleResponse }
     * 
     */
    public NewSaleResponse createNewSaleResponse() {
        return new NewSaleResponse();
    }

    /**
     * Create an instance of {@link NewSale }
     * 
     */
    public NewSale createNewSale() {
        return new NewSale();
    }

    /**
     * Create an instance of {@link Sale }
     * 
     */
    public Sale createSale() {
        return new Sale();
    }

    /**
     * Create an instance of {@link SaleItem }
     * 
     */
    public SaleItem createSaleItem() {
        return new SaleItem();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://salesagg/", name = "newSale")
    public JAXBElement<NewSale> createNewSale(NewSale value) {
        return new JAXBElement<NewSale>(_NewSale_QNAME, NewSale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://salesagg/", name = "newSaleResponse")
    public JAXBElement<NewSaleResponse> createNewSaleResponse(NewSaleResponse value) {
        return new JAXBElement<NewSaleResponse>(_NewSaleResponse_QNAME, NewSaleResponse.class, null, value);
    }

}
