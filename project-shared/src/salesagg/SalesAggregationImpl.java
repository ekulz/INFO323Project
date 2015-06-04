package salesagg;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import javax.jws.WebService;
import rpc.dao.SalesDAO;
import rpc.domain.Sale;

@WebService(endpointInterface = "salesagg.ISalesAggregationService",
        serviceName = "SalesAggregation",
        portName = "SalesAggregationPort")
public class SalesAggregationImpl implements ISalesAggregationService{
    
    private final SalesDAO salesDao = new SalesDAO();

    @Override
    public void newSale(Sale sale) throws RemoteException{
        System.out.println("This sale will be aggregated...");
        System.out.println(sale);
        salesDao.addSale(sale);

    }
    
}
