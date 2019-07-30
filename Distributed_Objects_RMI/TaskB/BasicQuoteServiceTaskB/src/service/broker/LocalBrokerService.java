package service.broker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;


import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.girlpower.*;
import service.dodgydrivers.*;
import service.auldfellas.*;
import service.registry.ServiceRegistry;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {

	
	
	public static Registry registry; 


	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();
		
		try { //looking up service registry can throw an error so need try catch block
			 registry = LocateRegistry.getRegistry(); //used to access other services e.g. girlpower
			 
		for (String name : registry.list()) {
			
			
			if (name.startsWith("qs-")) {
				//look up all services, get quote frome each one
		        QuotationService service = (QuotationService) registry.lookup(name); //looking up registy
		        //QuotationService ddq = (QuotationService) registry.lookup("qs-DodgyDriversService"); //looking up registy
		        //QuotationService afq = (QuotationService) registry.lookup("qs-AuldFellasService"); //looking up registy
		        
		        //generate GPQService
		        
		        quotations.add(service.generateQuotation(info));
		        //quotations.add(ddq.generateQuotation(info));
		        //quotations.add(afq.generateQuotation(info));
				
			}
		}
	} catch(Exception rm) { //catch remote exception
		rm.printStackTrace();//print exception
	}

		return quotations;
	}

}
