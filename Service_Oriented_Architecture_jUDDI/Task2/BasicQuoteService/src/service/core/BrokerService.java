package service.core;

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.util.List;

import service.registry.Service;

/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
@WebService //Add for web service
public interface BrokerService extends Service {
	//return an array
	@WebMethod public Quotation[] getQuotations(ClientInfo info);
}
