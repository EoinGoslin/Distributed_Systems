package client;

import javax.xml.ws.Endpoint;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.dodgydrivers.DDQService;
import service.girlpower.GPQService;



public class Server {

	

	
	public static void main(String[] args) {
		
		
		Endpoint.publish("http://localhost:9000/BrokerService/getQuotations?wsdl", new LocalBrokerService());
		Endpoint.publish("http://localhost:9001/QuotationService/GPQService?wsdl", new GPQService());
		Endpoint.publish("http://localhost:9002/QuotationService/DDQService?wsdl", new DDQService());
		Endpoint.publish("http://localhost:9003/QuotationService/AFQService?wsdl", new AFQService());
	
		
	}

}
