package implm;

import java.util.HashMap;

import Interface.Channel;
import Listener.AcceptListener;

public class Broker extends Interface.Broker{
	
	public static final int DEFAULT_CAPACITY = 255;
	
	
	private HashMap<Integer, AcceptListener> binds;
	
	public Broker(String name) {
		super(name);
		binds = new HashMap<>();
		BrokerManager.addBroker(name, this);
	}
	
	@Override
	public boolean bind(int port, AcceptListener listener) {
		
		if(!isBind(port)) {
			binds.put(port, listener);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean unbind(int port) {
		if(isBind(port)) {
			binds.remove(port);
			return true;
		}
		return false;
	}
	
	@Override
	public MessageQueue connect(String name, int port) {
		Broker target = (Broker) BrokerManager.lookup(name);
		if(target != null && target.isBind(port)) {
			CircularBuffer read = new CircularBuffer(DEFAULT_CAPACITY);
			CircularBuffer write = new CircularBuffer(DEFAULT_CAPACITY);
			Channel c = new implm.Channel(read, write);
			return target.accept(port, c, write, read);
		}
		return null;
		
	}

	@Override
	public MessageQueue accept(int port, Channel c, CircularBuffer read, CircularBuffer write){
		Channel channel = new implm.Channel(read, write);
		MessageQueue mqLocal = new MessageQueue((implm.Channel) channel);
		MessageQueue mqRemote = new MessageQueue((implm.Channel) c);
		mqLocal.setRemoteMQ(mqRemote);
		binds.get(port).accepted(mqLocal);
		return mqRemote;
	}
	
	public String getName() {
		return super.name;
	}
	
	@Override
	public boolean isBind(int port) {
		return binds.get(port) != null;
	}

}
