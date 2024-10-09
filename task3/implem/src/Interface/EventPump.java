package Interface;

public abstract class EventPump extends Thread {
	
	public abstract void post(Event e);
	public abstract void kill();
}
