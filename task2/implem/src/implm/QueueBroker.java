package implm;

import Interface.Broker;
import Interface.MessageQueue;

public class QueueBroker extends Interface.QueueBroker{

	public QueueBroker(Broker broker) {
		super(broker);
	}

	@Override
	public MessageQueue accept(int port) {
		Channel channel = (Channel) this.b.accept(port);
		return new implm.MessageQueue(channel);
	}

	@Override
	public MessageQueue connect(String name, int port) {
		Channel channel = (Channel) this.b.connect(name, port);
		return new implm.MessageQueue(channel);
	}

}
