package tests.listeners;

import Interface.MessageQueue;
import Interface.QueueBroker;
import Listener.AcceptListener;

public class AcceptListenerTest implements AcceptListener{
	
	private QueueBroker qb;
	private int port;
	
	public AcceptListenerTest(QueueBroker qb, int port) {
		this.qb = qb;
	}

	@Override
	public void accepted(MessageQueue queue) {
		queue.setListener(new EchoServerMessageListener(queue));
		qb.unbind(port);
	}

}
