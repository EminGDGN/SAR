package Interface;

import Listener.AcceptListener;
import Listener.ConnectListener;

public abstract class QueueBroker {
	
	protected Broker b;

	public QueueBroker(String name) {
		if(this.getClass() == QueueBroker.class)
			throw new IllegalStateException("Abstract class QueueBroker must not be instanciate");
		b = new implm.Broker(name);
	}

	public String name() {
		return b.getName();
	}
	
	public abstract boolean bind(int port, AcceptListener listener);
	public abstract boolean unbind(int port);
	public abstract boolean connect(String name, int port, ConnectListener listener);
}
