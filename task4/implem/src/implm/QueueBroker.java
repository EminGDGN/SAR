package implm;


import Event.BindEvent;
import Event.ConnectEvent;
import Event.UnbindEvent;
import Listener.AcceptListener;
import Listener.ConnectListener;

public class QueueBroker extends Interface.QueueBroker{

	public QueueBroker(String name) {
		super(name);
	}

	@Override
	public boolean bind(int port, AcceptListener listener) {
		if(super.b.isBind(port))
			return false;
		new BindEvent(this, port, listener);
		return true;
	}
	
	public void _bind(int port, AcceptListener listener) {
		super.b.bind(port, listener);
		
	}

	@Override
	public boolean unbind(int port) {
		if(super.b.isBind(port)) {
			new UnbindEvent(this, port);
			return true;
		}
		return false;
	}
	
	public void _unbind(int port) {
		super.b.unbind(port);
	}

	@Override
	public boolean connect(String name, int port, ConnectListener listener) {
		Broker target = (Broker) BrokerManager.lookup(name);
		if(target == null)
			return false;
		
		new ConnectEvent(this, name, port, listener);
		return true;
	}
	
	public void _connect(String name, int port, ConnectListener listener) {
		MessageQueue mq = (MessageQueue) super.b.connect(name, port);
		if(mq == null)
			listener.refused();
		else
			listener.connected(mq);
	}
}
