package service.core;


import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Interface to define the behaviour of a quotation service.
 * 
 * @author Rem
 *
 */
public interface QuotationService extends Remote {
	public Quotation generateQuotation(ClientInfo info) throws java.rmi.RemoteException;
	
	
}
