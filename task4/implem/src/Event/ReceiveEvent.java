package Event;

import Interface.Event;
import Interface.IChannel;
import implm.Channel;

public class ReceiveEvent extends Event{

	private Channel c;
	
	public ReceiveEvent(IChannel c) {
		super();
		this.c = (Channel) c;
	}

	@Override
	public void run() {
		c._read();
		
	}
}
