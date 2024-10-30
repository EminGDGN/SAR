package Event;


import Interface.Event;
import Interface.IBroker;
import implm.Broker;

public class UnbindEvent extends Event{

	private int port;
	private Broker b;
	
	public UnbindEvent(IBroker b, int port) {
		super();
		this.port = port;
		this.b = (Broker) b;
	}
	
	@Override
	public void run() {
		b._unbind(port);
	}
}
