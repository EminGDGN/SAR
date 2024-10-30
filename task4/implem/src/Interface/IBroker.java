package Interface;

import Listener.BrokerAcceptListener;
import Listener.BrokerConnectListener;
import implm.CircularBuffer;

public abstract class IBroker {
	
	protected String name;
	
	public IBroker(String name) {
		
		if(this.getClass() == IBroker.class) {
			throw new IllegalCallerException("Broker class is abstract");
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract boolean bind(int port, BrokerAcceptListener listener);
	public abstract boolean unbind(int port);
    public abstract void accept(int port, IChannel c, CircularBuffer read, CircularBuffer write);
    public abstract boolean connect(String name, int port, BrokerConnectListener l);
    public abstract boolean isBind(int port);
}
