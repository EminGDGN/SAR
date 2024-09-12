package Interface;

public abstract class Broker {
	
	private String name;
	
	public Broker(String name) {
		
		if(this.getClass() == Broker.class) {
			throw new IllegalCallerException("Broker class is abstract");
		}
		this.name = name;
	}
	
    public abstract Channel accept(int port);
    public abstract Channel connect(String name, int port);
}
