package service.dodgydrivers;

//UDDI imports
import org.apache.*;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.*;
import util.*;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.Name;

import service.broker.LocalBrokerService;
import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.girlpower.GPQService;

//Import ws libraries
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;

/**
 * Implementation of Quotation Service for Dodgy Drivers Insurance Company
 *  
 * @author Rem
 * uddi:juddi.apache.org:16d707ad-1343-4b50-a2c9-99b332cec542 business key
 */
@WebService(
		 serviceName="QuotationService", //this name must match service qName
		 targetNamespace="http://core.service/", //inverted package name of Broker interface, 
		 //as in web service uses interface so we want the implementation to look like the interface
		 portName="QuotationServicePort" //The portnName can be same for all services as Web service slots each service to a unique port 
		)
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) // format of wsdl need DOCUMENT as passing objects e.g. Quoatation
public class DDQService extends AbstractQuotationService implements QuotationService {
	
	//Each service needs a main to publish its service and register it on JUDDI
		public static void main (String[] args) {

			Endpoint.publish("http://localhost:9002/QuotationService/DDQService?wsdl", new DDQService());
			System.out.println("Running");
			
			if(WebServicesHelper.hasBusiness("DDQBusiness")) {
				System.out.println("Business already exists, not creating duplicate");
			} else {
				System.out.println("No business with that name exists, creating now...");
				WebServicesHelper.setupBusiness("DDQBusiness", "QuotationService", "http://localhost:9002/QuotationService/DDQService?wsdl"); // give a business and a service
			}
		
		}//End of main


	

	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "DD";
	public static final String COMPANY = "Dodgy Drivers Corp.";
	
	/**
	 * Quote generation:
	 * 5% discount per penalty point (3 points required for qualification)
	 * 50% penalty for <= 3 penalty points
	 * 10% discount per year no claims
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 800 and 1000
		double price = generatePrice(800, 200);
		
		// 5% discount per penalty point (3 points required for qualification)
		int discount = (info.points > 3) ? 5*info.points:-50;
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getNoClaimsDiscount(ClientInfo info) {
		return 10*info.noClaims;
	}
	

}
