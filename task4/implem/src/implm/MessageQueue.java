package implm;

import java.util.ArrayList;

import Interface.IChannel;
import Listener.Listener;

public class MessageQueue extends Interface.IMessageQueue{
	
	public class ChannelListener implements Listener{
		
		private Listener l;
		private Message m;
		
		public ChannelListener(Listener l) {
			this.l = l;
			this.m = null;
		}

		@Override
		public void received(byte[] msg) {
			if(m == null)
				m = new Message();
			m.addData(msg);
			if(m.fullyRead()) {
				l.received(m.getData());
				m = null;
			}
			
		}

		@Override
		public void closed() {
			l.closed();
			
		}

		@Override
		public void sent() {
			msgs.remove(0);
			if(msgs.size() > 0) {
				Message m = msgs.get(0);
				byte[] data = m.getData();
				c.write(data, 0, data.length);
			}
			
		}
	}
	
	private IChannel c;
	private ArrayList<Message> msgs;
	
	public MessageQueue(IChannel c) {
		this.c = c;
		this.msgs = new ArrayList<>();
	}
	
	@Override
	public boolean send(byte[] bytes) {
		return send(bytes, 0, bytes.length);
	}

	@Override
	public boolean send(byte[] bytes, int offset, int length) {
		if(c.disconnected())
			return false;
		
		Message m = new Message(bytes, offset, length);
		msgs.add(m);
		if(msgs.size() == 1) {
			byte[] data = m.getData();
			c.write(data, 0, data.length);
		}
		return true;
	}

	@Override
	public boolean close() {
		return c.disconnect();
	}

	@Override
	public boolean closed() {
		return c.disconnected();
	}

	@Override
	public void setListener(Listener l) {
		c.setListener(new ChannelListener(l));
	}
}
