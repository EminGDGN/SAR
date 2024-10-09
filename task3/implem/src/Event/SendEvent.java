package Event;

import Interface.Event;
import Listener.ConnectListener;
import implm.MessageQueue;
import implm.QueueBroker;

public class SendEvent extends Event{
	
	private MessageQueue mq;
	private byte[] buffer;
	private int offset;
	private int length;
	
	public SendEvent(MessageQueue mq, byte[] buffer, int offset, int length) {
		super();
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
		this.mq = mq;
	}

	@Override
	public void run() {
		if(mq._send(buffer, offset, length))
			this.kill();
		else
			super.post();
		
	}
	
	

}
