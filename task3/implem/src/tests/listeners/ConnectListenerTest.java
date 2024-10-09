package tests.listeners;

import Event.ReceiveEvent;
import Interface.MessageQueue;
import Listener.ConnectListener;

public class ConnectListenerTest implements ConnectListener{

	@Override
	public void connected(MessageQueue queue) {
		byte[] msg = "Client connected send message".getBytes();
		queue.setListener(new EchoClientMessageListener(queue));
		queue.send(msg);
		new ReceiveEvent((implm.MessageQueue) queue);
	}

	@Override
	public void refused() {
		// TODO Auto-generated method stub
		
	}

}
