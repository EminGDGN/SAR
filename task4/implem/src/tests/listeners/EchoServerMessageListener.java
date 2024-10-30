package tests.listeners;

import Listener.Listener;
import Interface.IMessageQueue;

public class EchoServerMessageListener implements Listener{
	
	private IMessageQueue mq;
	
	public EchoServerMessageListener(IMessageQueue mq) {
		this.mq = mq;
	}

	@Override
	public void received(byte[] msg) {
		System.out.println("server received message : " + new String(msg));
		mq.send(msg);
		
	}

	@Override
	public void closed() {
		System.out.println("Server Message queue closed");
		
	}

	@Override
	public void sent() {
		// TODO Auto-generated method stub
		
	}

}
