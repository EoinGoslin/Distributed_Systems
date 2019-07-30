package service.broker; 
//Imports
import java.util.LinkedList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.Service;
import util.*;

import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.FindQualifiers;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.api_v3.ServiceList;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDISecurityPortType;

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
	
	
	
	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:9000/BrokerService/getQuotations?wsdl", new LocalBrokerService());
		System.out.println("Running");
		if(WebServicesHelper.hasBusiness("brokerBusiness")) {
			System.out.println("Business already exists, not creating duplicate");
		} else {
			System.out.println("No business with that name exists, creating now...");
			// give a business, service and an endpoint
			WebServicesHelper.setupBusiness("brokerBusiness", "BrokerService", "http://localhost:9000/BrokerService/getQuotations?wsdl"); 
		}
		
	}

	
	public Quotation[] getQuotations(ClientInfo info) {
		

		//Accessing GPQService as a test
		UDDISecurityPortType security = null;
		
		UDDIInquiryPortType inquiry = null;
		List<Quotation> quotations = new LinkedList<Quotation>();


		
		
		try {
			UDDIClient client = new UDDIClient("META-INF/simple-browse-uddi.xml");
			Transport transport = client.getTransport("default");
			
			security = transport.getUDDISecurityService();
			inquiry = transport.getUDDIInquiryService();

		} catch (Exception e) {
			e.printStackTrace();
		}

				String token = WebServicesClientHelper.getAuthKey(security, "uddi", "uddi");
				//Then find businesses interested in
				try {
					
					ServiceList services = WebServicesClientHelper.partialServiceNameSearch(inquiry, token, "QuotationService" + UDDIConstants.WILDCARD);
					//ServiceInfo Sinfo = services.getServiceInfos().getServiceInfo().get(0);
	
					
					 GetServiceDetail gsd = new GetServiceDetail();
					for(int i = 0; i <services.getServiceInfos().getServiceInfo().size(); i++) { //run 3 times
						
				       
				 
				        	System.out.println("In loop");
				            gsd.getServiceKey().add(services.getServiceInfos().getServiceInfo().get(i).getServiceKey());
				        
				        System.out.println(inquiry.getServiceDetail(gsd));
					}
				     ServiceDetail serviceDetail = inquiry.getServiceDetail(gsd);
		
				     System.out.println(serviceDetail);
				     for (int k = 0; k < serviceDetail.getBusinessService().size(); k++) {
							System.out.println("serviceDetail.getBusinessService().size() is " + serviceDetail.getBusinessService().size());
							
							BindingTemplate bindingTemplate = serviceDetail.getBusinessService().get(k).getBindingTemplates().getBindingTemplate().get(0);
							System.out.println("Access: " + bindingTemplate.getBindingKey());
						    // Invoke the service…​
							//GETTING wsdlUrl FROM jUDDI and then using it to Create a Service
							URL wsdlUrl = new URL(bindingTemplate.getAccessPoint().getValue()); //get GPQServiceFrom from jUDDI
							System.out.println("wsdlURL is" + wsdlUrl);
							QName qname = new QName("http://core.service/", "QuotationService");
							
							Service service = Service.create(
									wsdlUrl, 
									new QName("http://core.service/", "QuotationService")
									);
							
							QuotationService qs =  service.getPort(
									new QName("http://core.service/", "QuotationServicePort"),
									QuotationService.class
									);
					
							
							quotations.add(qs.generateQuotation(info));	
						
						
						}//end of outer loop

				} catch(Exception e) {
					e.printStackTrace();//show where error came from
				}	
				
				return quotations.toArray(new Quotation[quotations.size()]); 
	}//end of method

	
}
