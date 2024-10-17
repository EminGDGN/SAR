package implm;

import java.util.Arrays;

import Event.CloseEvent;
import Event.ReceiveEvent;
import Event.SendEvent;
import Listener.Listener;
import implm.Channel.DisconnectedException;

public class MessageQueue extends Interface.MessageQueue{
	
	private Channel c;
	private Listener l;
	
	public MessageQueue(Channel c) {
		this.c = c;
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
	
	public void _send(byte[] bytes, int offset, int length) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					byte[] size = encodeLength(length);
					int len = size.length;
					byte[] sizeLen = {(byte)len};
					c.write(sizeLen, 0, 1);
					
					int channelStatus = 0;
					while(channelStatus != len) {
						channelStatus += c.write(size, channelStatus, len - channelStatus);
					}
					channelStatus = 0;
					while(channelStatus != length) {
						channelStatus += c.write(bytes, offset + channelStatus, length - channelStatus);
					}
				}
				catch(DisconnectedException e) {
					System.out.println("Message Queue disconnected");
				}
			}
		}).start();
	}

	public void _receive() {
		
		MessageQueue mq = this;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					byte[] size = new byte[1];
					c.read(size, 0, 1);
					int sizeLen = size[0];
					
					size = new byte[sizeLen];
					int channelStatus = 0;
					while(channelStatus != sizeLen) {
						channelStatus += c.read(size, channelStatus, sizeLen - channelStatus);
					}
					int len = decodeLength(size, sizeLen);
					
					channelStatus = 0;
					byte[] content = new byte[len];
					while(channelStatus != len) {
						channelStatus += c.read(content, channelStatus, len - channelStatus);
					}
					
					l.received(content);
					new ReceiveEvent(mq);
				}
				catch(DisconnectedException e) {
					l.closed();
				}
				
			}
		}).start();
	}

	@Override
	public boolean close() {
		if(c.disconnected())
			return false;
		new CloseEvent(this);
		return true;
		
	}
	
	public void _close() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				c.disconnect();
				l.closed();
			}
		}).start();
	}

	@Override
	public boolean closed() {
		return c.disconnected();
	}

	@Override
	public void setListener(Listener l) {
		this.l = l;
	}
	
	private byte[] encodeLength(int length) {
		if(length < 256) {
			byte[] result = {(byte)length};
			return result;
		}
		
		byte[] result;
		if(length < 65536) {
			result = new byte[2];
			result[0] = (byte) (length >> 8);
			result[1] = (byte) length;
			return result;
		}
		if(length < 16777216) {
			result = new byte[3];
			result[0] = (byte) (length >> 16);
			result[1] = (byte) (length >> 8);
			result[2] = (byte) length;
		}
		
		result = new byte[4];
		result[0] = (byte) (length >> 24);
		result[1] = (byte) (length >> 16);
		result[2] = (byte) (length >> 8);
		result[3] = (byte) length;
		return result;
	}
	
	private int decodeLength(byte[] size, int len) {
		int result = 0;
	    for (int i = 0; i < len; i++) {
	        result |= (size[i] & 0xFF) << ((len - i - 1) * 8);
	    }
	    return result;
	}

}
