package tests;

import Interface.Event;
import Interface.QueueBroker;
import implm.Task;
import tests.listeners.ConnectListenerTest;

public class EchoClient extends Event{

	private QueueBroker qb;
	
	public EchoClient(QueueBroker qb) {
		super();
		this.qb = qb;
	}

	@Override
	public void run() {
		qb.connect("server", 80, new ConnectListenerTest());
	}

}
