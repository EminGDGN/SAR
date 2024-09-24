package implm;


public class RDV {
	
	private boolean ready;
	private Broker owner;
	private Broker joiner;
	private int port;
	
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
			notifyAll();
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
}
