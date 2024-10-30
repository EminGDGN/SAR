package tests;


import Interface.Event;
import Interface.IQueueBroker;
import tests.listeners.AcceptListenerTest;

public class EchoServer extends Event{
	
	private IQueueBroker qb;

	public EchoServer(IQueueBroker qb) {
		super();
		this.qb = qb;
	}

	@Override
	public void run() {
		qb.bind(80, new AcceptListenerTest(qb, 80));
	}

}
