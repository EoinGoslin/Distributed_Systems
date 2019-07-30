package client;

import service.auldfellas.AFApplication;
import service.auldfellas.AFQService;
import service.broker.brokerApplication;
import service.dodgydrivers.DDApplication;
import service.girlpower.GPApplication;

public class Server {
public static void main (String[] args) throws Exception {
	//Start each of the resouces - making them available
	GPApplication.main(null);
	DDApplication.main(null);
	AFApplication.main(null);
	brokerApplication.main(null); //make broker available to client
}

}
