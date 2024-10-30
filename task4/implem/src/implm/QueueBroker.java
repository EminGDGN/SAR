package implm;

import Interface.IChannel;
import Interface.IMessageQueue;
import Listener.AcceptListener;
import Listener.BrokerAcceptListener;
import Listener.BrokerConnectListener;
import Listener.ConnectListener;

public class QueueBroker extends Interface.IQueueBroker{
	
	public class BrokerListener implements BrokerAcceptListener, BrokerConnectListener{

		private AcceptListener lAccept;
		private ConnectListener lConnect;
		
		public BrokerListener(AcceptListener l) {
			this.lAccept = l;
		}
		
		public BrokerListener(ConnectListener l) {
			this.lConnect = l;
		}
		
		@Override
		public void accepted(IChannel channel) {
			IMessageQueue mq = new MessageQueue(channel);
			lAccept.accepted(mq);
		}
		@Override
		public void connected(IChannel channel) {
			IMessageQueue mq = new MessageQueue(channel);
			lConnect.connected(mq);
		}
		@Override
		public void refused() {
			lConnect.refused();
			
		}
		
	}

	public QueueBroker(String name) {
		super(name);
	}

	@Override
	public boolean bind(int port, AcceptListener listener) {
		return super.b.bind(port, new BrokerListener(listener));
	}

	@Override
	public boolean unbind(int port) {
		return super.b.unbind(port);
	}
	
	@Override
	public boolean connect(String name, int port, ConnectListener listener) {
		return super.b.connect(name, port, new BrokerListener(listener));
	}
}
