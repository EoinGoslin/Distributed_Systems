package service.auldfellas;

//REST Services are organised into Applications
//An application is a set of related resources (Restlets) 
//possibly linked together through a Router.
//The Router maps each resource path to a Restlet that represents a single resource.
//This class combines multiple restlets 
//into a single application that will be deployed on a single node.

import java.util.HashMap;
import java.util.Map;
import service.core.ClientInfo;

import javax.imageio.spi.ServiceRegistry;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.routing.Router;

import com.google.gson.Gson;

import service.core.Quotation;



public class AFApplication extends Application { //Must extend Application
	static Map<String, Quotation> quotations = new HashMap<String,Quotation>();
	static Gson gson = new Gson();
	public AFQService afqs = new AFQService();
	
	
	public static void main (String[] args)  throws Exception {
		Component component = new Component();
        component.getServers().add(Protocol.HTTP, 10000);
        component.getDefaultHost().
            attach("", new AFApplication()); //left "" as only one application 
        component.start();
	}
	
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/quotations", new Restlet() {
		    public void handle(Request request, Response response) {
		    	
		         if (request.getMethod().equals(Method.POST)) { //Post request
		     
		            String json = request.getEntityAsText();
		            ClientInfo in = gson.fromJson(json, ClientInfo.class);
		            
		            Quotation quote = afqs.generateQuotation(in);
		            
		            
		            
		            if (!quotations.containsKey(quote.reference)) {
		            	System.out.println(quote.reference);
		            	quotations.put(quote.reference, quote);
		            	System.out.println(quotations.values());
		            
			            String out = "["; boolean first = true;
			            out += ",";
		            	String url= request.getHostRef()+"/quotation/"+quote.reference;
		            	System.out.println(url);
		            	response.setLocationRef(request.getHostRef()+"/quotation/"+quote.reference); 
		            	//response.setLocationRef(url); 
		               // String jSoned = gson.toJson(quote);
		                //System.out.println(jSoned);
		                
		                
		                //out += "{\"reference\" : \"" + quote.reference + "\", \"link\":\"" + url + "\"}";
		            	//response.setEntity(jSoned, MediaType.APPLICATION_JSON);
		            	response.setStatus(Status.SUCCESS_OK);
		            } 
		            else {
		            	System.out.println("hello");
		            	response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		            	
		            }
		        } else {
		        	
		        	response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		        }
		    }
		   
		  
		    
		});
		
		router.attach("/quotation/{reference}", new Restlet() { //{student number} is a parameter of the URL
		    @Override
		    public void handle(Request request, Response response) {
		        String id = (String) request.getAttributes().get("reference");
		        if (quotations.containsKey(id)) {
		            response.setStatus(Status.SUCCESS_OK);

		            if (request.getMethod().equals(Method.GET)) {
		                response.setEntity(gson.toJson(quotations.get(id)), MediaType.APPLICATION_JSON);
		                response.setStatus(Status.SUCCESS_OK);
		            } else if (request.getMethod().equals(Method.PUT)) {
		                Quotation quote = gson.fromJson(request.getEntityAsText(), Quotation.class);
		                quote.merge(quotations.get(id));
		                quotations.put(quote.reference, quote);
		                response.setStatus(Status.SUCCESS_NO_CONTENT);
		            }
		        } else {
		            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		        }
		    }
		});
		
		return router;
	}

}
