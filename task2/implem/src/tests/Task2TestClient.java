package tests;


import Interface.MessageQueue;
import Interface.QueueBroker;
import implm.Task;

class Task2TestClient implements Runnable{
	
	private String remoteName;
	private int remotePort;
	
	public Task2TestClient(String remoteName, int remotePort) {
		this.remoteName = remoteName;
		this.remotePort = remotePort;
	}

	@Override
	public void run() {
		
		QueueBroker qb = Task.getTask().getQueueBroker();
		MessageQueue messageQueue = qb.connect(remoteName, remotePort);
		byte[] buffer = new byte[255];
		for(int i = 1; i <= 255; i++) {
			buffer[i-1] = (byte)i;
		}
		messageQueue.send(buffer, 0, 255);
		
		byte[] response = messageQueue.receive();
		for(int i = 0; i < response.length; i++) {
			System.out.println("Client receives " + Integer.toString(response[i]));
		}
		
	}

}
