package tests;


import Interface.Broker;
import Interface.Channel;
import implm.Channel.DisconnectedException;
import implm.Task;

class Task1TestClient implements Runnable{
	
	private String remoteName;
	private int remotePort;
	
	public Task1TestClient(String remoteName, int remotePort) {
		this.remoteName = remoteName;
		this.remotePort = remotePort;
	}

	@Override
	public void run() {
		
		Broker b = Task.getBroker();
		Channel clientChannel = b.connect(remoteName, remotePort);
		byte[] buffer = new byte[255];
		for(int i = 1; i <= 255; i++) {
			buffer[i-1] = (byte)i;
		}
		
		int clientStatus = 0;
		int serverStatus = 0;
		int offset = 0;
		byte[] response = new byte[255];
		
		while(clientStatus != 255) {
			clientStatus = clientChannel.write(buffer, clientStatus, 255 - clientStatus);
			offset = clientChannel.read(response, offset, clientStatus);
			for(int i = serverStatus; i < offset; i++) {
				System.out.println("Client receives " + Integer.toString(response[i]));
			}
			serverStatus = offset;
		}
		clientChannel.disconnect();
		try {
			clientChannel.write(buffer, 0, 255);
		}catch(DisconnectedException e) {
			System.out.println("channel disconnected");
		}
		
	}

}
