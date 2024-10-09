package Interface;

import Listener.Listener;

public abstract class MessageQueue {
	
	public abstract boolean send(byte[] bytes);
	public abstract boolean send(byte[] bytes, int offset, int length);
	public abstract void setListener(Listener l);
	public abstract void close();
	public abstract boolean closed();
}
