/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import social.ISocialIntegrationService;

/**
 *
 * @author hiclu995
 */
public class SocialIntegrationServiceImpl extends UnicastRemoteObject implements ISocialIntegrationService {

    public SocialIntegrationServiceImpl() throws RemoteException {}
    
    
    @Override
    public void postNotification(String message) throws RemoteException {
        System.out.println(message);
    }    
}
