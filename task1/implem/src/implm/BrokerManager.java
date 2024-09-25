package implm;

import java.util.HashMap;

import Interface.Broker;

public class BrokerManager {

	private static HashMap<String,Broker> brokers = new HashMap<String, Broker>();
	
	public synchronized static void addBroker(String name, Broker b) {
		if(lookup(name) != null)
			throw new IllegalStateException("Broker already exists");
		brokers.put(name, b);
	}
	
	public static Broker lookup(String name) {
		return brokers.get(name);
	}
	
}
