package tests.utils;

import implm.QueueBroker;
import tests.EchoClient;
import tests.EchoServer;
import tests.listeners.ConnectListenerTest;

public class EchoClientRunnable implements Runnable{

	@Override
	public void run() {
		QueueBroker qb = (QueueBroker) EchoClient.getTask().getQueueBroker();
		qb.connect("server", 80, new ConnectListenerTest());
	}

}
