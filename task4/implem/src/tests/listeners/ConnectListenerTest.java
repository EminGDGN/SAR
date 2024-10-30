package tests.listeners;

import Interface.IMessageQueue;
import Listener.ConnectListener;

public class ConnectListenerTest implements ConnectListener{

	@Override
	public void connected(IMessageQueue queue) {
		byte[] msg = "Client connected send message".getBytes();
		queue.setListener(new EchoClientMessageListener(queue));
		queue.send(msg);
		queue.send(msg);
	}

	@Override
	public void refused() {
		System.out.println("Connection refused");
		
	}

}
