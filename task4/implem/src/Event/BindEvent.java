package Event;

import Interface.Event;
import Interface.IBroker;
import Listener.BrokerAcceptListener;
import implm.Broker;
import implm.QueueBroker;

public class BindEvent extends Event{
	
	private int port;
	private BrokerAcceptListener listener;
	private Broker b;
	
	public BindEvent(IBroker b, int port, BrokerAcceptListener listener) {
		super();
		this.port = port;
		this.listener = listener;
		this.b = (Broker) b;
	}
	
	@Override
	public void run() {
		b._bind(port, listener);
	}

}
