package service.auldfellas;

import service.broker.LocalBrokerService;
import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
//Import ws libraries
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;
import util.*;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.Name;


/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *uddi:juddi.apache.org:a969e1c4-fdc3-461f-8c25-59536f56f094 busiensss key
 */
@WebService(
		 serviceName="QuotationService", //this name must match service qName
		 targetNamespace="http://core.service/", //inverted package name of Broker interface, 
		 //as in web service uses interface so we want the implementation to look like the interface
		 portName="QuotationServicePort" //The portnName can be same for all services as Web service slots each service to a unique port 
		)
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) // format of wsdl
public class AFQService extends AbstractQuotationService implements QuotationService {
	
	
	//Each service needs a main to publish its service and register it on JUDDI
		public static void main (String[] args) {
			
			
			Endpoint.publish("http://localhost:9003/QuotationService/AFQService?wsdl", new AFQService());
			System.out.println("Running");
			
			if(WebServicesHelper.hasBusiness("AFQBusiness")) {
				System.out.println("Business already exists, not creating duplicate");
			} else {
				System.out.println("No business with that name exists, creating now...");
				WebServicesHelper.setupBusiness("AFQBusiness", "QuotationService", "http://localhost:9003/QuotationService/AFQService?wsdl"); // give a business and a service
			}
			
			
		}

	
	
	
	
	
	// All references are to be prefixed with an AF (e.g. AF001000)
	public static final String PREFIX = "AF";
	public static final String COMPANY = "Auld Fellas Ltd.";
	
	/**
	 * Quote generation:
	 * 30% discount for being male
	 * 2% discount per year over 60
	 * 20% discount for less than 3 penalty points
	 * 50% penalty (i.e. reduction in discount) for more than 60 penalty points 
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1200
		double price = generatePrice(600, 600);
		
		// Automatic 30% discount for being male
		int discount = (info.gender == ClientInfo.MALE) ? 30:0;
		
		// Automatic 2% discount per year over 60...
		discount += (info.age > 60) ? (2*(info.age-60)) : 0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getPointsDiscount(ClientInfo info) {
		if (info.points < 3) return 20;
		if (info.points <= 6) return 0;
		return -50;
		
	}
	
	

}
