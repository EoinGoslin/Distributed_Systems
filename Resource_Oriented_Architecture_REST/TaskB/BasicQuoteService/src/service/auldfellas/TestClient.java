package service.auldfellas;
import java.io.IOException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.NamedValue;
import org.restlet.util.Series;

import com.google.gson.Gson;

import client.Main;
import service.core.Quotation;
public class TestClient {
 @SuppressWarnings("unchecked")
 public static void main(String[] args)throws ResourceException, IOException { 
 Gson gson = new Gson();
 ClientResource client = new ClientResource("http://localhost:9000/quotations");

 
 client.post(gson.toJson(Main.clients[0])); //send a client info
 String location =((Series<NamedValue<String>>)client.getResponseAttributes().get("org.restlet.http.headers")).getFirstValue("Location");
 System.out.println("URL: " + location);
 new ClientResource(location).get().write(System.out);
 }
 
 
 
} 
