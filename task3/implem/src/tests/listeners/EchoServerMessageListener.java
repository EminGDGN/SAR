package tests.listeners;

import Listener.Listener;
import Interface.MessageQueue;

public class EchoServerMessageListener implements Listener{
	
	private MessageQueue mq;
	
	public EchoServerMessageListener(MessageQueue mq) {
		this.mq = mq;
	}

	@Override
	public void received(byte[] msg) {
		System.out.println("server receiverd message : " + new String(msg));
		mq.send(msg);
		
	}

	@Override
	public void closed() {
		mq.close();
		
	}

}
