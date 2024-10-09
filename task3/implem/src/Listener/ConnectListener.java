package Listener;

import Interface.MessageQueue;

public interface ConnectListener {
	public void connected(MessageQueue queue);
	public void refused();
}
