package client;
//TaskB - finished
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
import service.girlpower.GPQService;


public class Client {

	
	public static Registry registry;

	public static void main(String[] args) {
        String host = "";
        int port = 0;
        //If arguments passed is 2, then user can decide where to lookip in registry
        //e.g. - localhost, 1099
		try {
        	if(args.length == 2) {
        		host = args[0];
        		port = Integer.parseInt(args[1]);
        		registry = LocateRegistry.getRegistry(host, port);
        	} else {
        		
        	
        registry = LocateRegistry.getRegistry();
        	}
            BrokerService brokerService = (BrokerService) registry.lookup("BROKER_SERVICE"); //looking up registy

		// Create the broker and run the test data
		for (ClientInfo info : clients) { //For all the clients, get a quote from each service for that client
			displayProfile(info);
			
			// Retrieve quotations from the broker and display them...
			for(Quotation quotation : brokerService.getQuotations(info)) {
				
				displayQuotation(quotation);
			}
			
			// Print a couple of lines between each client
			System.out.println("\n");
        }
        }
        catch(Exception re) {
            re.printStackTrace();
            re.getMessage();
        }
        
    }
    

    public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| License Number: " + String.format("%1$-19s", info.licenseNumber) + 
				" | No Claims: " + String.format("%1$-24s", info.noClaims+" years") +
				" | Penalty Points: " + String.format("%1$-19s", info.points)+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	
	
	/**
	 * Test Data
	 * Used as sample clients, each client will receive different quotations from the insurance companies
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")		
	};


}
