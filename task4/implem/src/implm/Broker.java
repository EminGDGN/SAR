package implm;

import java.util.HashMap;

import Event.AcceptEvent;
import Event.BindEvent;
import Event.ConnectEvent;
import Event.UnbindEvent;
import Interface.Event;
import Interface.IChannel;
import Listener.BrokerAcceptListener;
import Listener.BrokerConnectListener;

public class Broker extends Interface.IBroker{
	
	public static final int DEFAULT_CAPACITY = 5;
	
	
	private HashMap<Integer, BrokerAcceptListener> binds;
	
	public Broker(String name) {
		super(name);
		binds = new HashMap<>();
		BrokerManager.addBroker(name, this);
	}
	
	@Override
	public boolean bind(int port, BrokerAcceptListener listener) {
		
		if(!isBind(port)) {
			new BindEvent(this, port, listener);
			return true;
		}
		return false;
	}
	
	public void _bind(int port, BrokerAcceptListener listener) {
		binds.put(port, listener);
	}
	
	@Override
	public boolean unbind(int port) {
		if(isBind(port)) {
			new UnbindEvent(this, port);
			return true;
		}
		return false;
	}
	
	public void _unbind(int port) {
		binds.remove(port);
	}
	
	@Override
	public boolean connect(String name, int port, BrokerConnectListener l) {
		Broker target = (Broker) BrokerManager.lookup(name);
		if(target != null) {
			new ConnectEvent(this, name, port, l);
			return true;
		}
		return false;
		
	}
	
	public void _connect(Event e, String name, int port, BrokerConnectListener l) {
		Broker target = (Broker) BrokerManager.lookup(name);
		if(target != null && target.isBind(port)) {
			CircularBuffer read = new CircularBuffer(DEFAULT_CAPACITY);
			CircularBuffer write = new CircularBuffer(DEFAULT_CAPACITY);
			IChannel c = new implm.Channel(read, write);
			new AcceptEvent(target, port, c, write, read);
			l.connected(c);
		}
		else {
			new ConnectEvent(e.getRemainingTries(), target, name, port, l);
		}
	}

	@Override
	public void accept(int port, IChannel c, CircularBuffer read, CircularBuffer write){
		IChannel channel = new Channel(read, write);
		channel.setRemote(c);
		c.setRemote(channel);
		binds.get(port).accepted(channel);
	}
	
	public String getName() {
		return super.name;
	}
	
	@Override
	public boolean isBind(int port) {
		return binds.get(port) != null;
	}

}
