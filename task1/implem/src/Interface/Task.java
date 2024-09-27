package Interface;

public abstract class Task extends Thread{
	
	private Broker b;
	private Runnable r;
	
	public Task(Broker b, Runnable r){
		if(this.getClass() == Task.class)
			throw new IllegalCallerException("Task class is abstract");
		this.b = b;
		this.r = r;
	}
	
	public Broker getBrok() {
		return b;
	}
	
    public static Broker getBroker() {
    	return ((Task)Thread.currentThread()).getBrok();
    }
    
    @Override
    public void run() {
        r.run();
    }
}
