package Interface;

public abstract class Broker {
	
	public Broker(String name) {
		throw new IllegalCallerException("Broker class is abstract");
	}
	
    abstract Channel accept(int port);
    abstract Channel connect(String name, int port);
}
