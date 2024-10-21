package implm;

import java.util.ArrayList;
import java.util.HashMap;

import Interface.Channel;

public class Broker extends Interface.Broker{
	
	
	private ArrayList<RDV> rdvs;
	private ArrayList<Channel> channels;
	private int stopAccept;

	public Broker(String name) {
		super(name);
		rdvs = new ArrayList<>();
		channels = new ArrayList<>();
		BrokerManager.addBroker(name, this);
		stopAccept = -1;
	}

	@Override
	public synchronized Channel accept(int port){
		if(this.getAcceptRdv(port) != null)
			throw new IllegalStateException("Accept on same port already pending");
		
		RDV rdv = this.getConnectRdv(port);
		Channel channel;
		if(rdv == null) {
			rdv = this.createRDV(this, port);
			while(!rdv.getReadyState() && stopAccept == -1) {
				try {
					wait();
				}
				catch (InterruptedException e) {}
			}
			if(stopAccept == port) {
				stopAccept = -1;
				return null;
			}
			
			channel = createChannel(true, rdv);
		}
		else {
			rdv.join(this, true);
			channel = createChannel(false, rdv);
		}
		
		this.removeRdv(rdv);
		channels.add(channel);
		return channel;
	}

	@Override
	public Channel connect(String name, int port) {
		Broker target = (Broker) BrokerManager.lookup(name);
		if(target != null) {
			synchronized(target) {
				RDV rdv = target.getAcceptRdv(port);
				Channel channel;
				if (rdv == null) {
					rdv = this.askRDV(target, port);
					channel = createChannel(true, rdv);
				}
				else {
					boolean joined = rdv.join(this, false);
					if(!joined) {
						rdv = this.askRDV(target, port);
						channel = createChannel(true, rdv);
					}
					else {
						channel = createChannel(false, rdv);
					}
				}
				
				return channel;
			}
		}
		return null;
		
	}
	
	public Channel createChannel(boolean owner, RDV rdv) {
		if(owner)
			return new implm.Channel(rdv.getOwnerReadCircularBuffer(), rdv.getOwnerWriteCircularBuffer());
		return new implm.Channel(rdv.getOwnerWriteCircularBuffer(), rdv.getOwnerReadCircularBuffer());
	}
	
	public RDV askRDV(Broker target, int port) {
		RDV rdv = target.createRDV(this, port);
		while(!rdv.getReadyState()) {
			try {
				target.wait();
			}
			catch (InterruptedException e) {}
		}
		return rdv;
	}
	
	public synchronized RDV createRDV(Broker b, int port) {
		RDV rdv = new RDV(b, port);
		rdvs.add(rdv);
		notifyAll();
		return rdv;
	}
	
	public synchronized RDV getAcceptRdv(int port) {
		for(RDV rdv : rdvs) {
			if(rdv.getPort() == port && rdv.isOwner(this)) {
				return rdv;
			}
		}
		return null;
	}
	
	public synchronized RDV getConnectRdv(int port) {
		for(RDV rdv : rdvs) {
			if(rdv.getPort() == port && !rdv.isOwner(this)) {
				return rdv;
			}
		}
		return null;
	}
	
	public void removeRdv(RDV rdv) {
		rdvs.remove(rdv);
	}
	
	public String getName() {
		return super.name;
	}
	
	public void setStopAccept(int port) {
		this.stopAccept = port;
	}

}
