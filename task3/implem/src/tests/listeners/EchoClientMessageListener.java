package tests.listeners;

import Listener.Listener;
import Interface.MessageQueue;

public class EchoClientMessageListener implements Listener{
	
	private MessageQueue mq;
	
	public EchoClientMessageListener(MessageQueue mq) {
		this.mq = mq;
	}

	@Override
	public void received(byte[] msg) {
		System.out.println("client received message : " + new String(msg));
		mq.close();
	}

	@Override
	public void closed() {
		mq.close();
		
	}

}
