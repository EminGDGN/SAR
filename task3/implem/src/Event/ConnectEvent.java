package Event;


import Listener.ConnectListener;
import implm.QueueBroker;

public class ConnectEvent extends Event{
	
	private int port;
	private ConnectListener listener;
	private String name;
	private QueueBroker qb;
	
	public ConnectEvent(QueueBroker qb, String name, int port, ConnectListener listener) {
		super();
		this.name = name;
		this.port = port;
		this.listener = listener;
		this.qb = qb;
	}
	
	@Override
	public void react() {
		if(qb._connect(name, port, listener)) {
			this.kill();
		}
		else {
			super.post();
		}
	}

}
