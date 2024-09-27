package tests;

import Interface.Broker;
import implm.Task;

public class Main {
	
	public static void main(String[] args) {
		Broker client = new implm.Broker("client");
		Broker server = new implm.Broker("server");
		
		new Task(client, new Task1TestClient("server", 80)).start();
		new Task(server, new Task1TestServer(80)).start();
	}
}
