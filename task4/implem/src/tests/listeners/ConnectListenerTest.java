package tests.listeners;

import Interface.MessageQueue;
import Listener.ConnectListener;

public class ConnectListenerTest implements ConnectListener{

	@Override
	public void connected(MessageQueue queue) {
		byte[] msg = "Client connected send message".getBytes();
		queue.setListener(new EchoClientMessageListener(queue));
		queue.send(msg);
	}

	@Override
	public void refused() {
		System.out.println("Connection refused");
		
	}

}
