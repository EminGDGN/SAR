package implm;

import java.util.LinkedList;
import java.util.List;

import Interface.Event;
import Interface.EventPump;

public class Executor extends EventPump {
	private List<Event> queue;
	public static Executor instance = null;
	
	public static Executor getInstance() {
		if(instance == null)
			instance = new Executor();
		return instance;
	}
	
	private Executor() {
		queue = new LinkedList<Event>();
	}
	
	@Override
	public void run() {
		Event e;
		while(true) {
			while (!queue.isEmpty()) {
				e = queue.remove(0);
				e.run();
			}
			sleep();
		}
	}
	
	@Override
	public synchronized void post(Event e) {
		queue.add(e);
		notify();
	}
	
	private synchronized void sleep() {
		try {
			wait();
		} catch (InterruptedException ex){
			// nothing to do here.
		}
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}