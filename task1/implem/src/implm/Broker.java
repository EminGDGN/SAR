package implm;

import Interface.Channel;

public class Broker extends Interface.Broker{

	public Broker(String name) {
		super(name);
	}

	@Override
	public Channel accept(int port) {
	}

	@Override
	public Channel connect(String name, int port) {
		
	}

}
