package implm;

import implm.Channel.DisconnectedException;

public class MessageQueue extends Interface.MessageQueue{
	
	private Channel c;
	
	public MessageQueue(Channel c) {
		this.c = c;
	}

	@Override
	public void send(byte[] bytes, int offset, int length) {
		byte[] size = new byte[4];
		size[0] = (byte) (length >> 24);
		size[1] = (byte) (length >> 16);
		size[2] = (byte) (length >> 8);
		size[3] = (byte) length;
		
		try {
			int channelStatus = 0;
			while(channelStatus != 4) {
				channelStatus += c.write(size, channelStatus, 4 - channelStatus);
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

	@Override
	public byte[] receive() {
		byte[] size = new byte[4];
		try {
			int channelStatus = 0;
			while(channelStatus != 4) {
				channelStatus += c.read(size, channelStatus, 4 - channelStatus);
			}
			
			int len = ((size[0] & 0xFF) << 24) |
		             ((size[1] & 0xFF) << 16) |
		             ((size[2] & 0xFF) << 8) |
		             (size[3] & 0xFF);
			
			channelStatus = 0;
			byte[] content = new byte[len];
			while(channelStatus != len) {
				channelStatus += c.read(content, channelStatus, len - channelStatus);
			}
			
			return content;
		}
		catch(DisconnectedException e) {
			System.out.println("Message Queue disconnected");
			return null;
		}
	}

	@Override
	public void close() {
		c.disconnect();
		
	}

	@Override
	public boolean closed() {
		return c.disconnected();
	}

}
