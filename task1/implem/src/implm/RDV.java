package implm;


public class RDV {
	
	private static final int DEFAULT_CAPACITY = 50;
	
	private boolean ready;
	private Broker owner;
	private Broker joiner;
	private int port;
	private CircularBuffer in;
	private CircularBuffer out;
	
	public RDV(Broker owner, int port) {
		this.owner = owner;
		this.joiner = null;
		ready = false;
		this.port = port;
	}
	
	public synchronized boolean join(Broker b) {
		if(joiner != null) {
			ready = true;
			joiner = b;
			in = new CircularBuffer(DEFAULT_CAPACITY);
			out = new CircularBuffer(DEFAULT_CAPACITY);
			owner.notifyAll();
			return true;
		}
		return false;
	}
	
	public boolean isOwner(Broker b) {
		return b == this.owner;
	}
	
	public boolean getReadyState() {
		return ready;
	}
	
	public int getPort() {
		return port;
	}
	
	public Broker getOwner() {
		return owner;
	}
	
	public Broker getJoiner() {
		return joiner;
	}
	
	public CircularBuffer getOwnerReadCircularBuffer() {
		return this.in;
	}
	
	public CircularBuffer getOwnerWriteCircularBuffer() {
		return this.out;
	}
}
