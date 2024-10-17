package Event;


import Interface.Event;
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
	public void run() {
		qb._connect(name, port, listener);
	}

}
