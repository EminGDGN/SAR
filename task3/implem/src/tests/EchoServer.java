package tests;


import Interface.Event;
import Interface.QueueBroker;
import tests.listeners.AcceptListenerTest;

public class EchoServer extends Event{
	
	private QueueBroker qb;

	public EchoServer(QueueBroker qb) {
		super();
		this.qb = qb;
	}

	@Override
	public void run() {
		qb.bind(80, new AcceptListenerTest(qb, 80));
	}

}
