package tests;

import Interface.Broker;
import Interface.QueueBroker;
import implm.Task;

public class Main {
	
	public static void main(String[] args) {
		Broker client = new implm.Broker("client");
		Broker server = new implm.Broker("server");
		
		QueueBroker bc = new implm.QueueBroker(client);
		QueueBroker bs = new implm.QueueBroker(server);
		
		new Task(bc, new Task2TestClient("server", 80)).start();
		new Task(bc, new Task2TestClient("server", 80)).start();
		new Task(bs, new Task2TestServer(80)).start();
	}
}
