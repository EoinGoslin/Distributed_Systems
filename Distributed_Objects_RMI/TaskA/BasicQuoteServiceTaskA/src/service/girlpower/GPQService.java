package service.girlpower;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the Girl Power insurance quotation service.
 * 
 * @author Rem
 *
 */
public class GPQService extends AbstractQuotationService implements QuotationService, Serializable {
	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "GP";
	public static final String COMPANY = "Girl Power Inc.";
	public static final String GIRL_POWER_SERVICE = "qs-GirlPowerService";  

	public static Registry registry; 
	
	//Create main so service can be registered
	public static void main (String...args) {
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
				//Otherwise connect localhost default port too
				registry = LocateRegistry.getRegistry();
			}
	
		
		
		//Need try block as UnicastRemoteObject can throw Exception
		//registry = LocateRegistry.getRegistry();
		QuotationService gpqObject = (QuotationService) UnicastRemoteObject.exportObject(new GPQService(), 0); //Turn object into a stream of Bytes
        registry.bind(GIRL_POWER_SERVICE,gpqObject);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Quote generation:
	 * 50% discount for being female
	 * 20% discount for no penalty points
	 * 15% discount for < 3 penalty points
	 * no discount for 3-5 penalty points
	 * 100% penalty for > 5 penalty points
	 * 5% discount per year no claims
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1000
		double price = generatePrice(600, 400);
		
		// Automatic 50% discount for being female
		int discount = (info.gender == ClientInfo.FEMALE) ? 50:0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getNoClaimsDiscount(ClientInfo info) {
		return 5*info.noClaims;
	}

	private int getPointsDiscount(ClientInfo info) {
		if (info.points == 0) return 20;
		if (info.points < 3) return 15;
		if (info.points < 6) return 0;
		return -100;
		
	}

}
