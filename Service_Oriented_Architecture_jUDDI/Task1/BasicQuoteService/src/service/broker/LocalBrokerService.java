package service.broker; 
//Imports
import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.registry.ServiceRegistry;
//Import ws libraries
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@WebService(
		 serviceName="BrokerService",
		 targetNamespace="http://core.service/", //inverted package name of Broker interface
		 portName="BrokerServicePort"
		)
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) // format of wsdl
// need Document as parameter passed is not primitive
public class LocalBrokerService implements BrokerService {
	//publish wsdl
	public static void main(String[] args) {
		//When searching online include this format:
//		http://localhost:9000/BrokerService/getQuotations?wsdl - need ?wsdl
		Endpoint.publish("http://localhost:9000/BrokerService/getQuotations", new LocalBrokerService());
//		Can view Schema then because DOCUMENT format with for example
//		http://localhost:9000/BrokerService/getQuotations?xsd=1
	}
	public Quotation[] getQuotations(ClientInfo info) {
		//Change to return an array
		List<Quotation> quotations = new LinkedList<Quotation>();
		
		for (String name : ServiceRegistry.list()) {
			if (name.startsWith("qs-")) {
				QuotationService service = ServiceRegistry.lookup(name, QuotationService.class);
				quotations.add(service.generateQuotation(info));
			}
		}
		//return an array
		return quotations.toArray(new Quotation[quotations.size()]); 


	}
	
}
