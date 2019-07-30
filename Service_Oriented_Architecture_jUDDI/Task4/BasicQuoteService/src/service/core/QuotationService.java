package service.core;

import javax.jws.WebMethod;
import javax.jws.WebService;



/**
 * Interface to define the behaviour of a quotation service.
 * 
 * @author Rem
 *
 */
//Change to be a web method as in an interface need @WebService
@WebService //Add for web service
public interface QuotationService  {
	@WebMethod public Quotation generateQuotation(ClientInfo info);
	
	
}
