package Interface;

import Listener.AcceptListener;
import implm.CircularBuffer;

public abstract class Broker {
	
	protected String name;
	
	public Broker(String name) {
		
		if(this.getClass() == Broker.class) {
			throw new IllegalCallerException("Broker class is abstract");
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract boolean bind(int port, AcceptListener listener);
	public abstract boolean unbind(int port);
    public abstract MessageQueue accept(int port, Channel c, CircularBuffer read, CircularBuffer write);
    public abstract MessageQueue connect(String name, int port);
    public abstract boolean isBind(int port);
}
