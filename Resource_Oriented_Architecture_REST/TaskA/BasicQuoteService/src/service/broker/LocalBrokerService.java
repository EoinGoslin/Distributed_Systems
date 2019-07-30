package service.broker;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.NamedValue;
import org.restlet.util.Series;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import client.Main;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.registry.ServiceRegistry;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {
	//Broker service knows of the urls
	public static final String[] SERVICE_URLS = {
			"http://localhost:10000/quotations", 
			"http://localhost:9999/quotations",
			"http://localhost:9998/quotations"
			}; //create array of URLs
	
	
	
	
	
	
	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();
		 Gson gson = new Gson();
		
		for (String name : SERVICE_URLS) {
			 try {

			    System.out.println("In local broker");
			    
				ClientResource client = new ClientResource(name); // name is a url
				client.post(gson.toJson(info)); //send a client info
       			String location =((Series<NamedValue<String>>)client.getResponseAttributes().get("org.restlet.http.headers")).getFirstValue("Location");
				ClientResource quoationResource = new ClientResource(location);
				String json =  quoationResource.get().getText();
				Quotation q = gson.fromJson(json, Quotation.class);
				quotations.add(q);

			
		
		
	} catch(Exception e){}
		}

		 
		 
		 
		return quotations;
	}	

	
}


