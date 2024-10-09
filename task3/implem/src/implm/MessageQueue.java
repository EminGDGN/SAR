package implm;

import java.util.Arrays;

import Event.SendEvent;
import Listener.Listener;
import implm.Channel.DisconnectedException;

public class MessageQueue extends Interface.MessageQueue{
	
	private Channel c;
	private Listener l;
	private Message reception;
	
	public MessageQueue(Channel c) {
		this.c = c;
		reception = null;
	}
	
	@Override
	public boolean send(byte[] bytes) {
		return send(bytes, 0, bytes.length);
	}

	@Override
	public boolean send(byte[] bytes, int offset, int length) {
		if(c.disconnected())
			return false;
		new SendEvent(this, bytes, offset, length);
		return true;
	}
	
	public boolean _send(byte[] bytes, int offset, int length) {
		Message msg = Message.getMessage(bytes);
		if(msg == null) {
			msg = new Message(bytes, offset, length);
		}
		
		int len = msg.getLengthSize();
		byte[] sizeLen = {(byte)len};
		c.write(sizeLen, 0, 1);
		
		int offsetMsg = msg.getOffset();
		int lengthMsg = msg.getLength();
		
		int channelStatus = c.write(msg.getBuffer(), offsetMsg, lengthMsg);
		if(channelStatus + offsetMsg != lengthMsg) {
			msg.setOffset(channelStatus + offsetMsg);
			msg.setLength(lengthMsg - channelStatus);
			return false;
		}
		return true;
	}

	public boolean _receive() {
		if(reception == null)
			reception = new Message();
		
		byte[] msg = new byte[255];
		int channelStatus = c.read(msg, 0, 255);
		byte[] data = Arrays.copyOf(msg, channelStatus);
		reception.addBuffer(data);
		
		if(reception.done()) {
			this.l.received(reception.getBuffer(reception.getOffset(), reception.getLength()));
			reception = null;
			return true;
		}
		return false;
	}

	@Override
	public void close() {
		c.disconnect();
		
	}

	@Override
	public boolean closed() {
		return c.disconnected();
	}

	@Override
	public void setListener(Listener l) {
		this.l = l;
	}

}
