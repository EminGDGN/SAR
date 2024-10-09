package tests;

import implm.Executor;
import implm.QueueBroker;
import tests.utils.EchoClientRunnable;
import tests.utils.EchoServerRunnable;

public class Main {
	
	public static void main(String[] args) {
		
		QueueBroker server = new QueueBroker("server");
		QueueBroker client = new QueueBroker("client");
		Executor exec = Executor.getInstance();
		
		exec.start();
		
		
		EchoServer es = new EchoServer(server, new EchoServerRunnable());
		EchoClient ec = new EchoClient(client, new EchoClientRunnable());
		
		es.start();
		ec.start();
	}
}
