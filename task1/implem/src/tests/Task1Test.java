package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Interface.Broker;
import Interface.Channel;

class Task1Test {
	
	private final String SERVER_NAME = "serverhost";
	private final int SERVER_PORT = 80;
	
	private final String CLIENT_NAME = "clienthost";
	private final int CLIENT_PORT = 800;

	private Broker server;
	private Broker client;
	
	private Channel serverChannel;
	private Channel clientChannel;
	
	@BeforeEach
    public void setUp() {
        //TODO : Replace with correct implementation of Broker and Channel
		serverChannel = server.accept(SERVER_PORT);
		clientChannel = client.connect(SERVER_NAME, SERVER_PORT);
    }
	
	
	@Test
	void noBugTest() {
		
		byte[] buffer = new byte[255];
		for(int i = 1; i <= 255; i++) {
			buffer[i-1] = (byte)i;
		}
		
		int clientStatus = clientChannel.write(buffer, 0, 255);
		if(clientStatus < 0) {
			throw new AssertionError("Channel failed");
		}
		
		byte[] response = new byte[255];
		int serverStatus = serverChannel.read(response, 0, 255);
		if(serverStatus == 255) {
			for(int i = 0; i < 255; i++) {
				if(i + 1 != (int)response[i]) {
					throw new AssertionError("Value not corresponding");
				}
			}
		}
		else {
			throw new AssertionError("Channel failed");
		}
		
		clientChannel.disconnect();
		if(!serverChannel.disconnected()) {
			throw new AssertionError("Channel disconnect failed");
		}
	}
	
	@Test
	void ChannelErrorTest() {
		byte[] buffer = new byte[255];
		for(int i = 1; i <= 255; i++) {
			buffer[i-1] = (byte)i;
		}
		
		serverChannel.disconnect();
		
		int clientStatus = clientChannel.write(buffer, 0, 255);
		if(clientStatus > 0) {
			throw new AssertionError("Logic failed, Server channel is disconnected");
		}
	}

}
