package implm;

import java.util.Arrays;

import Event.CloseEvent;
import Event.ReceiveEvent;
import Event.WriteEvent;
import Interface.IChannel;
import Listener.Listener;

public class Channel extends Interface.IChannel{
	
	public class DisconnectedException extends RuntimeException {

	    public DisconnectedException(String s) {
	        super("The channel has been disconnected.");
	    }

	}
	
	private CircularBuffer readBuffer;
	private CircularBuffer writeBuffer;
	private boolean disconnected;
	private IChannel remote;
	private Listener l;
	
	public Channel(CircularBuffer readBuffer, CircularBuffer writeBuffer) {
		this.readBuffer = readBuffer;
		this.writeBuffer = writeBuffer;
		disconnected = false;
		remote = null;
		l = null;
	}
	
	public void _read(){
		if(disconnected)
			throw new DisconnectedException("");
		
		int length = Broker.DEFAULT_CAPACITY;
		byte[] bytes = new byte[length];
		int i = 0;
		while(i < length) {
			
			try {
				byte b = readBuffer.pull();
				bytes[i] = b;
				i++;
			}
			catch (IllegalStateException e) {
				break;
			}
		}
		
		if(l != null) {
			l.received(Arrays.copyOf(bytes, i));
		}
	}

	@Override
	public boolean write(byte[] bytes, int offset, int length){
		if(disconnected)
			return false;
		new WriteEvent(this, bytes, offset, length);
		return true;
	
	}
	
	public void _write(byte[] bytes, int offset, int length) {
		if(disconnected)
			throw new DisconnectedException("");
		
		int i = 0;
		while(i < length) {
			try {
				writeBuffer.push(bytes[i + offset]);
				i++;
			}
			catch(IllegalStateException e) {
				break;
			}
		}
		new ReceiveEvent(remote);
		if(i != length) {
			new WriteEvent(this, bytes, offset + i, length - i);
		}else {
			l.sent();
		}
		
	}

	@Override
	public boolean disconnect(){
		if(disconnected)
			return false;
		new CloseEvent(this);
		return true;
		
	}
	
	public void _disconnect() {
		if(disconnected)
			return;
		if(readBuffer.empty()) {
			disconnected = true;
			l.closed();
			remote.disconnect();
		} else {
			new CloseEvent(this);
		}
	}

	@Override
	public boolean disconnected() {
		return disconnected;
	}
	
	public void setRemote(IChannel c) {
		this.remote = c;
	}

	@Override
	public void setListener(Listener l) {
		this.l = l;
		
	}
}
