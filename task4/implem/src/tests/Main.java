package tests;

import implm.Executor;
import implm.QueueBroker;

public class Main {
	
	public static void main(String[] args) {
		
		QueueBroker server = new QueueBroker("server");
		QueueBroker client1 = new QueueBroker("client1");
		Executor exec = Executor.getInstance();
		
		new EchoServer(server);
		new EchoClient(client1);
		exec.start();
	}
}
