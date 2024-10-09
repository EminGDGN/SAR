package Event;


import Interface.Event;
import implm.QueueBroker;

public class UnbindEvent extends Event{

	private int port;
	private QueueBroker qb;
	
	public UnbindEvent(QueueBroker qb, int port) {
		super();
		this.port = port;
		this.qb = qb;
	}
	
	@Override
	public void run() {
		if(qb._unbind(port)) {
			this.kill();
		}
		else {
			super.post();
		}
	}
}
