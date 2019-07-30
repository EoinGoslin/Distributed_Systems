package util;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.Name;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDISecurityPortType;

public class WebServicesHelper {
	public static BusinessService createWSDLService(String serviceName, String myBusKey, String endpointUrl) {
		// Create a new business service
		BusinessService myService = new BusinessService();
		myService.setBusinessKey(myBusKey);
		Name myServName = new Name();
		myServName.setValue(serviceName);
		myService.getName().add(myServName);

		BindingTemplates myBindingTemplates = new BindingTemplates();
		
		// Create a WSDL/SOAP binding Template
		BindingTemplate myBindingTemplate = new BindingTemplate();
		AccessPoint accessPoint = new AccessPoint();
		accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
		accessPoint.setValue(endpointUrl);
		myBindingTemplate.setAccessPoint(accessPoint);
		// Add the tModel for Soap/Http...
		myBindingTemplate = UDDIClient.addSOAPtModels(myBindingTemplate);
		myBindingTemplates.getBindingTemplate().add(myBindingTemplate);
		
		// Add it to the set of binding templates for the service
		myService.setBindingTemplates(myBindingTemplates);
		
		return myService;
	}

	public static String createBusiness(String businessName, UDDIClerk clerk) {
		// Step 1: Creating the parent business entity that will contain our
		// service.
		BusinessEntity myBusEntity = new BusinessEntity();
		Name myBusName = new Name();
		myBusName.setValue(businessName);
		myBusEntity.getName().add(myBusName);
		
		// Adding the business entity to the "save" structure, using our
		// publisher's authentication info and saving away.
		BusinessEntity register = clerk.register(myBusEntity);
		if (register == null) {
			System.out.println("Save failed!");
			System.exit(1);
		}
		return register.getBusinessKey();
	}
	
	//check if business exists already
	public static boolean hasBusiness(String businessName) { //check if jUDDI already has that business

		UDDISecurityPortType security = null;
		
		UDDIInquiryPortType inquiry = null;
	
		
		try {
			UDDIClient client = new UDDIClient("META-INF/simple-browse-uddi.xml");
			Transport transport = client.getTransport("default");
			
			security = transport.getUDDISecurityService();
			inquiry = transport.getUDDIInquiryService();
			String token = WebServicesClientHelper.getAuthKey(security, "uddi", "uddi");
			
			BusinessList businesslist = WebServicesClientHelper.partialBusinessNameSearch(inquiry, token, businessName);
			BusinessInfo bInfo = businesslist.getBusinessInfos().getBusinessInfo().get(0);
			System.out.println("Business Key is: " + bInfo.getBusinessKey());
			return true; //true, a business exists
			
			
			
			
			
			
		
			
		
	} catch(Exception e) { //catch Exception
		e.printStackTrace();
			
			
		}
		
		System.out.println("No business with that name exists");
		return false; //return false as no business is present as nullPointerException thrown by .get(0)
	
		
	}
	
	
	
	public static void setupBusiness(String businessName, String businessService, String endPoint) {

		UDDIClerk clerk = null;

			try {
				UDDIClient uddiClient = new UDDIClient("META-INF/uddi.xml");
				clerk = uddiClient.getClerk("default");
				
				if(clerk == null) {
			        throw new Exception("the clerk wasn't found, check the config file!");
				}
				
				//Generate business key
				String BusinessKey = WebServicesHelper.createBusiness(businessName, clerk);
				
				//Register Business
				BusinessService myService = WebServicesHelper.createWSDLService(businessService, BusinessKey, endPoint);
				System.out.println("Business Key is" + "\n" + BusinessKey);
				//Publish Business
				BusinessService svc = clerk.register(myService);
				
				if(svc == null) {
					System.out.println("Save failed!");
					System.exit(1);
				}
				String myServKey = svc.getServiceKey();					
				clerk.discardAuthToken();
			} catch(Exception e) {
				e.printStackTrace();
			
		}
			
	}

	
}
