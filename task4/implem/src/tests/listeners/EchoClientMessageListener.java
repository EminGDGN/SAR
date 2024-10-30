package tests.listeners;

import Listener.Listener;
import Interface.IMessageQueue;

public class EchoClientMessageListener implements Listener{
	
	private IMessageQueue mq;
	
	public EchoClientMessageListener(IMessageQueue mq) {
		this.mq = mq;
	}

	@Override
	public void received(byte[] msg) {
		System.out.println("client received message : " + new String(msg));
		mq.close();
	}

	@Override
	public void closed() {
		System.out.println("Client Message queue closed");
		
	}

	@Override
	public void sent() {
		// TODO Auto-generated method stub
		
	}

}
