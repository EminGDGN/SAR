package tests;

import Interface.QueueBroker;
import implm.Task;

public class EchoClient extends Task{

	public EchoClient(QueueBroker qb, Runnable r) {
		super(qb, r);
	}

}
