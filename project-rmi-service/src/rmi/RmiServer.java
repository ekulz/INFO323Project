package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import social.ISocialIntegrationService;

public class RmiServer {

   public static void main(String[] args) throws RemoteException {

       
      //IShoppingListService service = new ShoppingListServiceImpl();
        ISocialIntegrationService service = new SocialIntegrationServiceImpl();

        Registry registry = LocateRegistry.createRegistry(1099);

        registry.rebind("social", service);

        System.out.println("RMI Service Ready.");

   }
}
