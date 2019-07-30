package service.broker;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.registry.ServiceRegistry;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {



	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();
		try { //looking up service registry can throw an error so need try catch block
			
		for (String name : ServiceRegistry.list()) {
			
			if (name.startsWith("qs-")) {
				QuotationService service = ServiceRegistry.lookup(name, QuotationService.class);
				quotations.add(service.generateQuotation(info));
			}
		}
	} catch(Exception rm) { //catch remote exception
		rm.printStackTrace();//print exception
	}

		return quotations;
	}


}
