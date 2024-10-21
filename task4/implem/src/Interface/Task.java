package Interface;

public abstract class Task extends Thread{
	
	private Broker b;
	private QueueBroker qb;
	private Runnable r;
	
	public Task(Broker b, Runnable r){
		if(this.getClass() == Task.class)
			throw new IllegalCallerException("Task class is abstract");
		this.b = b;
		this.r = r;
	}
	
	public Task(QueueBroker qb, Runnable r){
		if(this.getClass() == Task.class)
			throw new IllegalCallerException("Task class is abstract");
		this.qb = qb; 
		this.r = r;
	}
	
	public Broker getBroker() {
		return b;
	}
	
	public QueueBroker getQueueBroker() {
		return qb;
	}
	
    public static Task getTask() {
    	return (Task)Thread.currentThread();
    }
    
    @Override
    public void run() {
        r.run();
    }
}
