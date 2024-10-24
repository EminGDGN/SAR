package Event;

import Interface.Event;
import implm.MessageQueue;

public class SendEvent extends Event{
	
	private MessageQueue mq;
	
	public SendEvent(MessageQueue mq) {
		super();
		this.mq = mq;
	}

	@Override
	public void run() {
		mq._send();
		
	}
	
	

}
