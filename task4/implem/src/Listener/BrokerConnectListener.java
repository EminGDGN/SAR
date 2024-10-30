package Listener;

import Interface.IChannel;

public interface BrokerConnectListener {
	public void connected(IChannel channel);
	public void refused();
}
