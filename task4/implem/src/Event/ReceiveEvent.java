package Event;

import Interface.Event;
import implm.MessageQueue;

public class ReceiveEvent extends Event{

	private MessageQueue mq;
	
	public ReceiveEvent(MessageQueue mq) {
		super();
		this.mq = mq;
	}

	@Override
	public void run() {
		mq._receive();
		
	}
}
