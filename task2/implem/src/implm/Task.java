package implm;

import Interface.Broker;
import Interface.QueueBroker;

public class Task extends Interface.Task{

	public Task(Broker b, Runnable r) {
		super(b, r);
	}
	
	public Task(QueueBroker qb, Runnable r) {
		super(qb, r);
	}

}
