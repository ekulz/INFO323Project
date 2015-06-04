package test;



import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import social.ISocialIntegrationService;

public class JUnitTestRMI {
    
    ISocialIntegrationService server = null;
    
    public JUnitTestRMI() {
    }
    
    @Before
    public void setUp() throws RemoteException, NotBoundException {
        
        //get remote registry
        String remoteAddress = "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(remoteAddress);
        server = (ISocialIntegrationService) registry.lookup("social");
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testRmi() throws RemoteException {
        server.postNotification("Social media account connected");
        assertTrue(true);
    }


}

