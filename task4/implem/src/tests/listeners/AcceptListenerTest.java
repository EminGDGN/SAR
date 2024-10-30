package tests.listeners;

import Interface.IMessageQueue;
import Interface.IQueueBroker;
import Listener.AcceptListener;
import tests.EchoClient;

public class AcceptListenerTest implements AcceptListener{
	
	private IQueueBroker qb;
	private int port;
	
	public AcceptListenerTest(IQueueBroker qb, int port) {
		this.qb = qb;
		this.port = port;
	}

	@Override
	public void accepted(IMessageQueue queue) {
		queue.setListener(new EchoServerMessageListener(queue));
		qb.unbind(port);
		IQueueBroker client2 = new implm.QueueBroker("client2");
		new EchoClient(client2);
	}

}
