package Interface;

import Listener.Listener;

public abstract class IChannel {
	
	public abstract boolean write(byte[] bytes, int offset, int length);
	public abstract boolean disconnect();
	public abstract boolean disconnected();
	public abstract void setRemote(IChannel c);
	public abstract void setListener(Listener l);
}
