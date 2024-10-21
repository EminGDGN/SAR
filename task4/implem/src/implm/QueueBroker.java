package implm;

import java.util.HashMap;

import Event.BindEvent;
import Event.ConnectEvent;
import Event.ReceiveEvent;
import Event.UnbindEvent;
import implm.MessageQueue;
import Listener.AcceptListener;
import Listener.ConnectListener;

public class QueueBroker extends Interface.QueueBroker{
	
	private HashMap<Integer, Task> binds;

	public QueueBroker(String name) {
		super(name);
		this.binds = new HashMap<>();
	}

	@Override
	public boolean bind(int port, AcceptListener listener) {
		synchronized (binds){
			if(binds.get(port) != null)
				return false;
		}
		
		new BindEvent(this, port, listener);
		return true;
	}
	
	public void _bind(int port, AcceptListener listener) {
		
		Task t = new Task(b, new Runnable() {
			@Override
			public void run() {
				while(true) {
					Channel channel = (Channel) b.accept(port);
					if(channel == null)
						break; //port unbinded
					MessageQueue mq = new MessageQueue(channel);
					new ReceiveEvent(mq);
					listener.accepted(mq);
				}
				
			}
		});
		binds.put(port, t);
		t.start();
	}

	@Override
	public boolean unbind(int port) {
		synchronized (binds){
			if(binds.get(port) == null)
				return false;
		}
		
		new UnbindEvent(this, port);
		return true;
	}
	
	public void _unbind(int port) {
		Task t = binds.get(port);
		if(t != null) {
			synchronized(b) {
				((Broker)t.getBroker()).setStopAccept(port);
				t.interrupt();
				binds.remove(port);
			}
		}
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
		new Task(b, new Runnable() {
			@Override
			public void run() {
				Channel channel = (Channel) b.connect(name, port);
				MessageQueue mq = new MessageQueue(channel);
				new ReceiveEvent(mq);
				listener.connected(mq);
			}
		}).start();
	}
}
