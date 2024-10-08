package Interface;

import implm.Executor;

public abstract class Event implements Runnable{
	
	protected Executor executor;
	private boolean done;
	
	public Event() {
		if(this.getClass() == Event.class)
			throw new IllegalStateException("Event class is abstract");
		executor = Executor.getInstance();
		done = false;
		this.post();
	}

	@Override
	public abstract void run();

	public void post() {
		executor.post(this);
	}
	
	public void kill() {
		done = true;
	}
	
	public boolean killed() {
		return done;
	}

}
