package Interface;

public abstract class QueueBroker {
	
	protected Broker b;

	public QueueBroker(Broker broker) {
		if(this.getClass() == QueueBroker.class)
			throw new IllegalStateException("Abstract class QueueBroker must not be instanciate");
		b = broker;
	}

	public String name() {
		return b.getName();
	}
	
	public abstract MessageQueue accept(int port);
	
	public abstract MessageQueue connect(String name, int port);
}
