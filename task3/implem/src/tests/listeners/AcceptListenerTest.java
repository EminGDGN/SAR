package tests.listeners;

import Event.ReceiveEvent;
import Interface.MessageQueue;
import Listener.AcceptListener;

public class AcceptListenerTest implements AcceptListener{

	@Override
	public void accepted(MessageQueue queue) {
		queue.setListener(new EchoServerMessageListener(queue));
		new ReceiveEvent((implm.MessageQueue) queue);
	}

}
