package tests.utils;

import implm.QueueBroker;
import tests.EchoServer;
import tests.listeners.AcceptListenerTest;

public class EchoServerRunnable implements Runnable{
	
	public EchoServerRunnable() {}

	@Override
	public void run() {
		QueueBroker qb = (QueueBroker) EchoServer.getTask().getQueueBroker();
		qb.bind(80, new AcceptListenerTest());
	}
	
}
