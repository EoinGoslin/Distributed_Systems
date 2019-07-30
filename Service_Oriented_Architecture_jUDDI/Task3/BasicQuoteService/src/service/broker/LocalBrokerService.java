package service.broker; 
//Imports
import java.util.LinkedList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import java.nio.charset.MalformedInputException;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

//Import ws libraries
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import client.Client;
import client.Server;


/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@WebService(
		 serviceName="BrokerService", //this name must match service qName
		 targetNamespace="http://core.service/", //inverted package name of Broker interface, 
		 //as in web service uses interface so we want the implementation to look like the interface
		 portName="BrokerServicePort" //The portnName can be same for all services as Web service slots each service to a unique port 
		)
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) // format of wsdl
// need Document as parameter passed is not primitive
public class LocalBrokerService implements BrokerService {


	
	
	public Quotation[] getQuotations(ClientInfo info) {
		
		//Change to return an array
		List<Quotation> quotations = new LinkedList<Quotation>();
		
		
		for (String name : Server.SERVICE_URLS) {
			
			
			try {
				URL wsdlUrl = new URL(name);

				//QName qname = new QName("http://core.service/", "QuotationService");
				//Service service = Service.create(wsdlUrl, qname);
				
				Service service = Service.create(
						new URL(name), 
						new QName("http://core.service/", "QuotationService")
						);
				
				QuotationService qs =  service.getPort(
						new QName("http://core.service/", "QuotationServicePort"),
						QuotationService.class
						); 
				quotations.add(qs.generateQuotation(info));
				

				
			} catch(MalformedURLException mf) {
				
				mf.printStackTrace();
			
				
			}
			
		}
		//return an array
		
		return quotations.toArray(new Quotation[quotations.size()]); 

	}
}
