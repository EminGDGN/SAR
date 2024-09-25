package Interface;

public abstract class Task extends Thread{
	
	private Broker b;
	
	public Task(Broker b, Runnable r){
		if(this.getClass() == Task.class)
			throw new IllegalCallerException("Task class is abstract");
		r.run();
	}
	
	public Broker getBrok() {
		return b;
	}
	
    public static Broker getBroker() {
    	return ((Task)Thread.currentThread()).getBrok();
    }
}
