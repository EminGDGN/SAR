package Interface;

public abstract class Task extends Thread{
	
	private Broker b;
	
	public Task(Broker b, Runnable r){
		throw new IllegalCallerException("Task class is abstract");
	}
	
	public Broker getBrok() {
		return b;
	}
	
    public static Broker getBroker() {
    	return ((Task)Thread.currentThread()).getBrok();
    }
}
