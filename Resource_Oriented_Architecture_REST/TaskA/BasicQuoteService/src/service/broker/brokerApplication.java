package service.broker;


import client.Main;
import com.google.gson.Gson;
import org.restlet.*;
import client.Main;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Router;
import org.restlet.util.NamedValue;
import org.restlet.util.Series;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
	

	

public class brokerApplication extends Application{

	static Gson gson = new Gson();
	public LocalBrokerService brk = new LocalBrokerService();
	ArrayList<Quotation> list_of_quotes = new ArrayList<>();
	// public AFQService afq = new AFQService();

	// public BrokerApplication = new BrokerApplication();

	public static void main(String[] args) throws Exception {
	Component component = new Component();
	component.getServers().add(Protocol.HTTP, 9003); 
	component.getClients().add(Protocol.HTTP);  //enable the HTTP protocol for clients as well as servers.
	component.getDefaultHost().attach("", new brokerApplication());
	component.start();
	}


	public Restlet createInboundRoot() {
	Router router = new Router(getContext());
	router.attach("/broker", new Restlet() {
	public void handle(Request request, Response response) {


	


	if (request.getMethod().equals(Method.POST)) {
		
	
	 String json = request.getEntityAsText(); //get information from the url
     ClientInfo in = gson.fromJson(json, ClientInfo.class); //generate client from json data using the client class as template
 	

    
     
	
	List quotations = new LocalBrokerService().getQuotations(in);
	System.out.println(quotations);
	//Add all the quotes to ArrayList so can post all quotes back for that person to the Main
	list_of_quotes.addAll(new LocalBrokerService().getQuotations(in));
	
	
	

	
	
	String toJson = gson.toJson(list_of_quotes); // getting all quotations and setting them into toJson
	
	response.setEntity(toJson, MediaType.APPLICATION_JSON); //returning a json object instead of a resource url
	list_of_quotes.clear(); //clear list as new person will come in from main
	
	response.setStatus(Status.SUCCESS_OK); //return 200
	} else
	response.setStatus(Status.CLIENT_ERROR_FORBIDDEN); // return 403
	}
	});
	return router;
	}
}

	

