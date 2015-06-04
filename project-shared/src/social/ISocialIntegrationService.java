
package social;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ISocialIntegrationService extends Remote {
    
    
    void postNotification(@WebParam(name = "notification") String message) throws RemoteException;
    
}
