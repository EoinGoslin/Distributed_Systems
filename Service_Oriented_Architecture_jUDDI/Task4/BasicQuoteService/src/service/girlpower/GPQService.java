package service.girlpower;


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
//Import ws libraries
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import javax.xml.ws.Endpoint;


import util.WebServicesHelper;

/**
 * Implementation of the Girl Power insurance quotation service.
 * 
 * @author Rem
 *
 */
@WebService(
		 serviceName="QuotationService", //this name must match service qName
		 targetNamespace="http://core.service/", //inverted package name of Broker interface, 
		 //as in web service uses interface so we want the implementation to look like the interface
		 portName="QuotationServicePort" //The portnName can be same for all services as Web service slots each service to a unique port 
		)
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) // format of wsdl
public class GPQService extends AbstractQuotationService implements QuotationService {
	
	//"http://localhost:9000/QuotationService/GPQService?wsdl"
	//I need to publish this link up as a service on JUDDI so localBroker can retrieve it
	//Each service needs a main to publish its service and register it on JUDDI
	public static void main (String[] args) {
		
		Endpoint.publish("http://localhost:9001/QuotationService/GPQService?wsdl", new GPQService());
		System.out.println("Running");
		
		if(WebServicesHelper.hasBusiness("GPQBusiness")) {
			System.out.println("Business already exists, not creating duplicate");
		} else {
			System.out.println("No business with that name exists, creating now...");
			WebServicesHelper.setupBusiness("GPQBusiness", "QuotationService", "http://localhost:9001/QuotationService/GPQService?wsdl"); // give a business and a service
		}
		
	}

		



	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "GP";
	public static final String COMPANY = "Girl Power Inc.";
	
	/**
	 * Quote generation:
	 * 50% discount for being female
	 * 20% discount for no penalty points
	 * 15% discount for < 3 penalty points
	 * no discount for 3-5 penalty points
	 * 100% penalty for > 5 penalty points
	 * 5% discount per year no claims
	 */
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1000
		double price = generatePrice(600, 400);
		
		// Automatic 50% discount for being female
		int discount = (info.gender == ClientInfo.FEMALE) ? 50:0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getNoClaimsDiscount(ClientInfo info) {
		return 5*info.noClaims;
	}

	private int getPointsDiscount(ClientInfo info) {
		if (info.points == 0) return 20;
		if (info.points < 3) return 15;
		if (info.points < 6) return 0;
		return -100;
		
	}
	
	

}
