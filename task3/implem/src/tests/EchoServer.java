package tests;


import implm.QueueBroker;
import implm.Task;

public class EchoServer extends Task{

	public EchoServer(QueueBroker qb, Runnable r) {
		super(qb, r);
	}

}
