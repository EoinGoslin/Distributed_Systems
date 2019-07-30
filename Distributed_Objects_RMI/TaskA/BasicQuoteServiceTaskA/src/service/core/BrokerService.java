package service.core;

import java.util.List;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
public interface BrokerService extends  Remote {
	public List<Quotation> getQuotations(ClientInfo info) throws java.rmi.RemoteException;
}