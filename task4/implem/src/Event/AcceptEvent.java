package Event;

import Interface.Event;
import Interface.IBroker;
import Interface.IChannel;
import implm.Broker;
import implm.CircularBuffer;

public class AcceptEvent extends Event{
	
	private Broker b;
	private int port;
	private IChannel c;
	private CircularBuffer read;
	private CircularBuffer write;
	
	public AcceptEvent(IBroker b, int port, IChannel c, CircularBuffer read, CircularBuffer write) {
		this.b = (Broker) b;
		this.c = c;
		this.port = port;
		this.read = read;
		this.write = write;
	}

	@Override
	public void run() {
		b.accept(port, c, read, write);
		
	}

}
