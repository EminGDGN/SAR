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
		new BindEvent(this, port, listener);
		return true;
	}
	
	public boolean _bind(int port, AcceptListener listener) {
		try {
			Channel channel = (Channel) this.b.accept(port);
			listener.accepted(new implm.MessageQueue(channel));
			return true;
		}
		catch(IllegalStateException e) {
			System.out.println("QueueBroker already have port " + port + " binded");
			return false;
		}
	}

	@Override
	public boolean unbind(int port) {
		new UnbindEvent(this, port);
		return true;
	}
	
	public boolean _unbind(int port) {
		implm.Broker b = (implm.Broker)this.b;
		b.removeRdv(b.getAcceptRdv(port));
		return true; //If port wasn't bind, nothing happens
	}

	@Override
	public boolean connect(String name, int port, ConnectListener listener) {
		new ConnectEvent(this, name, port, listener);
		return true;
	}
	
	public boolean _connect(String name, int port, ConnectListener listener) {
		Channel channel = (Channel) this.b.connect(name, port);
		listener.connected(new implm.MessageQueue(channel));
		return true;
	}

}
