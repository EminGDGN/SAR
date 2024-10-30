package Interface;

import implm.Executor;

public abstract class Event implements Runnable{
	
	public static int MAX_TRIES = 10;
	
	protected Executor executor;
	private boolean done;
	private int tries;
	
	public Event(int remainingTries) {
		if(this.getClass() == Event.class)
			throw new IllegalStateException("Event class is abstract");
		tries = --remainingTries;
		executor = Executor.getInstance();
		done = false;
		this.post();
	}
	
	public Event() {
		this(MAX_TRIES);
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
	
	public int getRemainingTries() {
		return tries;
	}

}
