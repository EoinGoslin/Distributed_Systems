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

			 
				ClientResource client = new ClientResource(name); // name is a url
				client.post(gson.toJson(info)); //send a client info	
				String location =((Series<NamedValue<String>>)client.getResponseAttributes().get("org.restlet.http.headers")).getFirstValue("Location"); //get resource location
				ClientResource quoationResource = new ClientResource(location); //Retrieve the resource using the location
				String json =  quoationResource.get().getText(); //get json data from resource
				Quotation q = gson.fromJson(json, Quotation.class); //from the json, create java object using Quotation class as the template
				quotations.add(q); //add the quotation to the List

			
		
		
	} catch(Exception e){}
			 
		}

		 
		 
		 
		return quotations;
	}	
	
}


