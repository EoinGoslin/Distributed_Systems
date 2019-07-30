package client;

import java.text.NumberFormat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.girlpower.*;
import service.registry.ServiceRegistry;
import service.core.*;

public class Server {
	public static final String GIRL_POWER_SERVICE = "qs-GirlPowerService";  
	public static final String AULD_FELLAS_SERVICE = "qs-AuldFellasService";  
	public static final String DODGY_DRIVERS_SERVICE = "qs-DodgyDriversService";
	public static Registry registry;


	public static void main(String[] args) {
        
        //Server should create Registry
        //Export object as a stream of Bytes
        //Bind to that registry so Client can look it up 
        //And then show a message that server is ready and running
		try {
            registry = LocateRegistry.createRegistry(1099); //can throw Remote Exception
            BrokerService BS = (BrokerService) UnicastRemoteObject.exportObject(new LocalBrokerService(), 0); //Turn object into a stream of Bytes
            registry.bind("BROKER_SERVICE",BS);
             
            QuotationService gpqObject = (QuotationService) UnicastRemoteObject.exportObject(new GPQService(), 0); //Turn object into a stream of Bytes
             registry.bind(GIRL_POWER_SERVICE,gpqObject);
             
             QuotationService ddqObject = (QuotationService) UnicastRemoteObject.exportObject(new DDQService(), 0); //Turn object into a stream of Bytes
			  registry.bind(DODGY_DRIVERS_SERVICE,ddqObject);
			  
			 QuotationService afqObject = (QuotationService) UnicastRemoteObject.exportObject(new AFQService(), 0); //Turn object into a stream of Bytes
			 registry.bind(AULD_FELLAS_SERVICE,afqObject);
			 
		
	  
	         
            
            System.out.println("RMI Server running on port: 1099");
            } catch(Exception rm){ //catch excpetion
                rm.printStackTrace();
    
            }
		while (true) {
			try {
			 System.out.println("Now starting Thread.sleep");
			 Thread.sleep(10000);
			} catch (InterruptedException e) {
			 e.printStackTrace();
			 }
			} 
        
            
	}
}