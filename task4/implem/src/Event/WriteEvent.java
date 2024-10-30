package Event;

import Interface.Event;
import Interface.IChannel;
import implm.Channel;

public class WriteEvent extends Event{
	
	private Channel c;
	private byte[] buffer;
	private int offset;
	private int length;
	
	public WriteEvent(IChannel c, byte[] buffer, int offset, int length) {
		super();
		this.c = (Channel) c;
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
	}

	@Override
	public void run() {
		c._write(buffer, offset, length);
		
	}
	
	

}
