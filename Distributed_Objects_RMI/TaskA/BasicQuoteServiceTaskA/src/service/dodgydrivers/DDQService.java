package service.dodgydrivers;

import java.io.Serializable;
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
 * Implementation of Quotation Service for Dodgy Drivers Insurance Company
 *  
 * @author Rem
 *
 */
public class DDQService extends AbstractQuotationService implements QuotationService, Serializable {
	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "DD";
	public static final String COMPANY = "Dodgy Drivers Corp.";
	public static final String DODGY_DRIVERS_SERVICE = "qs-DodgyDriversService";
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
		registry = LocateRegistry.getRegistry();
		 QuotationService ddqObject = (QuotationService) UnicastRemoteObject.exportObject(new DDQService(), 0); //Turn object into a stream of Bytes
		  registry.bind(DODGY_DRIVERS_SERVICE,ddqObject);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		}
	
	
	/**
	 * Quote generation:
	 * 5% discount per penalty point (3 points required for qualification)
	 * 50% penalty for <= 3 penalty points
	 * 10% discount per year no claims
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 800 and 1000
		double price = generatePrice(800, 200);
		
		// 5% discount per penalty point (3 points required for qualification)
		int discount = (info.points > 3) ? 5*info.points:-50;
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getNoClaimsDiscount(ClientInfo info) {
		return 10*info.noClaims;
	}

}
