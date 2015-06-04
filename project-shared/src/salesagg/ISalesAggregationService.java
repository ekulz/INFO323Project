package salesagg;

import java.rmi.Remote;
import java.rmi.RemoteException;
import rpc.domain.Sale;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface ISalesAggregationService extends Remote{
    
    void newSale(@WebParam(name = "sale") Sale sale) throws RemoteException;
    
}
