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

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
import java.text.NumberFormat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.girlpower.*;
import service.core.*;

public class LocalBrokerService implements BrokerService {

	
	
	public static Registry registry; 
	
	
	public static void main (String[] args) {
		String host = "";
		int port = 0;
		try { //looking up service registry can throw an error so need try catch block
			//Lookup registry, bind service to it, and client can go through broker and 
			//connect to any service it wants
			if(args.length == 2) {
				
				host = args[0];
				port = Integer.parseInt(args[1]);
				//parse as createRegistry() takes an int as an argument
				//e.g 1099
				registry = LocateRegistry.getRegistry(host, port);
			
			} else {
			//create Registry and then other services can loop it up and bind their service to it
			//Does not always have to run on localhost
			//If argument length 0, then localhost
			//Otherwise, run registry  on port and host specified
				//Otherwise place on 1099
				registry = LocateRegistry.getRegistry();
			}
			//registry = LocateRegistry.getRegistry(); //used to access other services e.g. girlpower
			 BrokerService BS = (BrokerService) UnicastRemoteObject.exportObject(new LocalBrokerService(), 0); //Turn object into a stream of Bytes
	         registry.bind("BROKER_SERVICE",BS);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();
		
//		try { //looking up service registry can throw an error so need try catch block
//			//Lookup registry, bind service to it, and client can go through broker and 
//			//connect to any service it wants
//			registry = LocateRegistry.getRegistry(); //used to access other services e.g. girlpower
//			 BrokerService BS = (BrokerService) UnicastRemoteObject.exportObject(new LocalBrokerService(), 0); //Turn object into a stream of Bytes
//	         registry.bind("BROKER_SERVICE",BS);
		try {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
//	} catch(Exception rm) { //catch remote exception
//		rm.printStackTrace();//print exception
//	}

		return quotations;
	}

}
