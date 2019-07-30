package client;

import javax.xml.ws.Endpoint;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.dodgydrivers.DDQService;
import service.girlpower.GPQService;



public class Server {


	public static final String[] SERVICE_URLS = {
			"http://localhost:9000/QuotationService/GPQService?wsdl", 
			"http://localhost:9000/QuotationService/DDQService?wsdl",
			"http://localhost:9000/QuotationService/AFQService?wsdl"
			}; //create array of URLs
	
	
	public static void main(String[] args) {
		
		
		Endpoint.publish("http://localhost:9000/BrokerService/getQuotations?wsdl", new LocalBrokerService());
		Endpoint.publish("http://localhost:9000/QuotationService/GPQService?wsdl", new GPQService());
		Endpoint.publish("http://localhost:9000/QuotationService/DDQService?wsdl", new DDQService());
		Endpoint.publish("http://localhost:9000/QuotationService/AFQService?wsdl", new AFQService());
		
	}

}