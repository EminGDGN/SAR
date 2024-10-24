package Event;

import Interface.Event;
import implm.MessageQueue;

public class CloseEvent extends Event{
	
private MessageQueue mq;
	
	public CloseEvent(MessageQueue mq) {
		super();
		this.mq = mq;
	}

	@Override
	public void run() {
		mq._close();
		
	}

}
