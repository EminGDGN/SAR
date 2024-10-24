package implm;


import java.util.ArrayList;

import Event.CloseEvent;
import Event.ReceiveEvent;
import Event.SendEvent;
import Listener.Listener;
import implm.Channel.DisconnectedException;

public class MessageQueue extends Interface.MessageQueue{
	
	private Channel c;
	private Listener l;
	private MessageQueue mq;
	private Message messageReceive;
	private ArrayList<Message> queueSend;
	
	public MessageQueue(Channel c) {
		this.c = c;
		this.mq = null;
		this.l = null;
		messageReceive = null;
		queueSend = new ArrayList<>();
	}
	
	@Override
	public boolean send(byte[] bytes) {
		return send(bytes, 0, bytes.length);
	}

	@Override
	public boolean send(byte[] bytes, int offset, int length) {
		if(c.disconnected())
			return false;
		queueSend.add(new Message(bytes));
		new SendEvent(this);
		return true;
	}
	
	public void _send() {
		try {
			Message m = queueSend.get(0);
			m.write(c);
			new ReceiveEvent(mq);
			if(m.fullyWrote()) {
				queueSend.remove(0);
			} else {
				new SendEvent(this);
			}
		} catch(DisconnectedException e) {
			l.closed();
		}
	}

	public void _receive() {
		if(messageReceive == null)
			messageReceive = new Message();
		messageReceive.addData(c);
		if(messageReceive.fullyRead()) {
			l.received(messageReceive.getData());
			messageReceive = null;
		}
	}

	@Override
	public boolean close() {
		if(c.disconnected())
			return false;
		new CloseEvent(this);
		return true;
		
	}
	
	public void _close() {
		c.disconnect();
		mq.close();
		l.closed();
	}

	@Override
	public boolean closed() {
		return c.disconnected();
	}

	@Override
	public void setListener(Listener l) {
		this.l = l;
	}
	
	public void setRemoteMQ(MessageQueue mq) {
		if(this.mq == null) {
			this.mq = mq;
			mq.setRemoteMQ(this);
		}
	}

}
