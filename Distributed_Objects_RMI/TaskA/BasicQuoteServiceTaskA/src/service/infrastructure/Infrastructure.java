package service.infrastructure;

import java.rmi.registry.LocateRegistry;

import java.text.NumberFormat;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.girlpower.*;
import service.core.*;


public class Infrastructure {
	public static Registry registry;

	public static void main (String[] args) {
		int port = 0;
		try {
			//Take in port through command line arguments
			
			if(args.length == 0) {
				
				//port = args[0];
				//parse as createRegistry() takes an int as an argument
				//e.g 1099
				registry = LocateRegistry.createRegistry(1099); 
			
			} else {
			//create Registry and then other services can loop it up and bind their service to it
			//Does not always have to run on localhost
			//If argument length 0, then localhost
			//Otherwise, run registry  on port and host specified
				//Otherwise place on 1099
				port = Integer.parseInt(args[0]);
				registry = LocateRegistry.createRegistry(port);
			}
	
		
		
		} catch(Exception e) {
		e.printStackTrace();
	}
		
		
		//While loop needed so connection stays alive for Client to connect
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
