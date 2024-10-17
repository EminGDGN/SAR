package Event;

import Interface.Event;
import Interface.Task;
import Listener.AcceptListener;
import implm.Channel;
import implm.QueueBroker;

public class BindEvent extends Event{
	
	private int port;
	private AcceptListener listener;
	private QueueBroker qb;
	
	public BindEvent(QueueBroker qb, int port, AcceptListener listener) {
		super();
		this.port = port;
		this.listener = listener;
		this.qb = qb;
	}
	
	@Override
	public void run() {
		qb._bind(port, listener);
	}

}
