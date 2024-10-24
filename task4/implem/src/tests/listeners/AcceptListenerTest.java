package tests.listeners;

import Interface.MessageQueue;
import Interface.QueueBroker;
import Listener.AcceptListener;
import tests.EchoClient;

public class AcceptListenerTest implements AcceptListener{
	
	private QueueBroker qb;
	private int port;
	
	public AcceptListenerTest(QueueBroker qb, int port) {
		this.qb = qb;
		this.port = port;
	}

	@Override
	public void accepted(MessageQueue queue) {
		queue.setListener(new EchoServerMessageListener(queue));
		qb.unbind(port);
		QueueBroker client2 = new implm.QueueBroker("client2");
		new EchoClient(client2);
	}

}
