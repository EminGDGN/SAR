package tests;

import Interface.Broker;
import Interface.Channel;
import implm.Task;
import implm.Channel.DisconnectedException;

public class Task1TestServer implements Runnable{
	
	private int port;
	
	public Task1TestServer(int port) {
		this.port = port;
	}
	
	
	private void work(Channel serverChannel) {
		try {
			while(true) {
				byte[] response = new byte[255];
				int serverStatus = serverChannel.read(response, 0, 255);
				
				for(int i = 0; i < serverStatus; i++) {
					System.out.println("Server receives " + response[i]);
				}
				
				serverChannel.write(response, 0, serverStatus);
			}
		}catch(DisconnectedException e) {
			System.out.println("Remote channel disconnected");
		}
	}

	@Override
	public void run() {
		
		Broker b = Task.getBroker();
		while(true) {
			Channel serverChannel = b.accept(port);
			work(serverChannel);
		}
		
	}

}
