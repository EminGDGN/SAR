package Listener;

import Interface.IMessageQueue;

public interface ConnectListener {
	public void connected(IMessageQueue queue);
	public void refused();
}
