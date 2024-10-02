package tests;

import Interface.Broker;
import Interface.Channel;
import Interface.MessageQueue;
import Interface.QueueBroker;
import implm.Task;
import implm.Channel.DisconnectedException;

public class Task2TestServer implements Runnable{
	
	private int port;
	
	public Task2TestServer(int port) {
		this.port = port;
	}
	
	
	private void work(MessageQueue messageQueue) {
		try {
			byte[] response;
			while((response = messageQueue.receive()) != null) {
				for(int i = 0; i < response.length; i++) {
					System.out.println("Server receives " + response[i]);
				}
				
				messageQueue.send(response, 0, response.length);
			}
		}catch(DisconnectedException e) {
			System.out.println("Remote channel disconnected");
			System.out.println("end of work");
		}
	}

	@Override
	public void run() {
		
		QueueBroker qb = Task.getTask().getQueueBroker();
		while(true) {
			MessageQueue messageQueue = qb.accept(port);
			work(messageQueue);
		}
		
	}

}
