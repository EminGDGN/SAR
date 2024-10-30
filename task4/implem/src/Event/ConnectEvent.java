package Event;


import Interface.Event;
import Interface.IBroker;
import Listener.BrokerConnectListener;
import Listener.ConnectListener;
import implm.Broker;
import implm.QueueBroker;

public class ConnectEvent extends Event{
	
	private int port;
	private BrokerConnectListener listener;
	private String name;
	private Broker b;
	
	public ConnectEvent(int tries, IBroker b, String name, int port, BrokerConnectListener listener) {
		super(tries);
		this.name = name;
		this.port = port;
		this.listener = listener;
		this.b = (Broker) b;
	}
	
	public ConnectEvent(IBroker b, String name, int port, BrokerConnectListener listener) {
		this(Event.MAX_TRIES, b, name, port, listener);
	}
	
	@Override
	public void run() {
		if(super.getRemainingTries() == 0)
			listener.refused();
		else
			b._connect(this, name, port, listener);
	}

}
