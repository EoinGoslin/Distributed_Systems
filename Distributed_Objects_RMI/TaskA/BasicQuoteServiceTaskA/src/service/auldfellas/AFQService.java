package service.auldfellas;

import java.io.Serializable;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
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
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class AFQService extends AbstractQuotationService implements QuotationService, Serializable {
	// All references are to be prefixed with an AF (e.g. AF001000)
	public static final String PREFIX = "AF";
	public static final String COMPANY = "Auld Fellas Ltd.";
	public static final String AULD_FELLAS_SERVICE = "qs-AuldFellasService";  
	public static Registry registry;

	
	
	public static void main (String[] args) {
		String host = "";
		int port = 0;
		try {
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
			
		//registry = LocateRegistry.getRegistry();
		QuotationService afqObject = (QuotationService) UnicastRemoteObject.exportObject(new AFQService(), 0); //Turn object into a stream of Bytes
		 registry.bind(AULD_FELLAS_SERVICE,afqObject);
		} catch(Exception e) {
			e.printStackTrace();
		}
		}
	
	/**
	 * Quote generation:
	 * 30% discount for being male
	 * 2% discount per year over 60
	 * 20% discount for less than 3 penalty points
	 * 50% penalty (i.e. reduction in discount) for more than 60 penalty points 
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1200
		double price = generatePrice(600, 600);
		
		// Automatic 30% discount for being male
		int discount = (info.gender == ClientInfo.MALE) ? 30:0;
		
		// Automatic 2% discount per year over 60...
		discount += (info.age > 60) ? (2*(info.age-60)) : 0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getPointsDiscount(ClientInfo info) {
		if (info.points < 3) return 20;
		if (info.points <= 6) return 0;
		return -50;
		
	}

	
}
