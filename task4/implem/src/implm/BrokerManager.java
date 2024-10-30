package implm;

import java.util.HashMap;

import Interface.IBroker;

public class BrokerManager {

	private static HashMap<String,IBroker> brokers = new HashMap<String, IBroker>();
	
	public synchronized static void addBroker(String name, IBroker b) {
		if(lookup(name) != null)
			throw new IllegalStateException("Broker already exists");
		brokers.put(name, b);
	}
	
	public static IBroker lookup(String name) {
		return brokers.get(name);
	}
	
}
