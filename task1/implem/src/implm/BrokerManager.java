package implm;

import java.util.HashMap;

import Interface.Broker;

public class BrokerManager {

	private static HashMap<String,Broker> brokers = new HashMap<String, Broker>();
	
	public static void addBroker(String name, Broker b) {
		brokers.put(name, b);
	}
	
	public static Broker lookup(String name) {
		return brokers.get(name);
	}
	
}
