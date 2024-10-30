package tests;

import Interface.Event;
import Interface.IQueueBroker;
import tests.listeners.ConnectListenerTest;

public class EchoClient extends Event{

	private IQueueBroker qb;
	
	public EchoClient(IQueueBroker qb) {
		super();
		this.qb = qb;
	}

	@Override
	public void run() {
		qb.connect("server", 80, new ConnectListenerTest());
	}

}
