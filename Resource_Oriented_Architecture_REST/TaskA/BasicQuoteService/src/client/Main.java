package client;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.util.NamedValue;
import org.restlet.util.Series;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.girlpower.GPQService;
import service.registry.ServiceRegistry;

public class Main {
//Sequence of order is Main -> brokerApplication(knows of client from main) -> localBrokerService(knows of urls of resources) -> each quotation resource -> localBrokerService -> brokerApplication -> client
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		ClientResource client = new ClientResource("http://localhost:9003/broker"); // name is a url

		 for (ClientInfo info : clients) {
		 
			 
			 
			 
			 
				String json = (client.post(gson.toJson(info)).getText()); //get json object back not just a url to find the resource
				System.out.println("This is json format '\n" + json); //show json format
			    Type type = new TypeToken<List<Quotation>>(){}.getType(); //put them in a json list and type is set to be Quotation
				List<Quotation> quotations = gson.fromJson(json, type);  //add three quotes per person to the list
				//THE LIST IS CLEARED EVERY TIME IN brokerApplication so that it prints correctly

		
		 displayProfile(info);

		  //Retrieve quotations from the broker and display them...
		 for(Quotation quotation : quotations) {
		 displayQuotation(quotation);
		 }

		 // Print a couple of lines between each client
		 System.out.println("\n");
		 }
		 
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
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
