package Event;

import Interface.Event;
import Interface.IChannel;
import implm.Channel;

public class CloseEvent extends Event{
	
private Channel c;
	
	public CloseEvent(IChannel c) {
		super();
		this.c = (Channel) c;
	}

	@Override
	public void run() {
		c._disconnect();
		
	}

}
