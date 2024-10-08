package Event;

import implm.MessageQueue;

public class ReceiveEvent extends Event{

	private MessageQueue mq;
	
	public ReceiveEvent(MessageQueue mq) {
		super();
		this.mq = mq;
	}

	@Override
	public void react() {
		if(mq._receive())
			this.kill();
		else
			super.post();
		
	}
}
