package implm;

public class Channel extends Interface.Channel{
	
	public class DisconnectedException extends RuntimeException {

	    public DisconnectedException(String s) {
	        super("The channel has been disconnected.");
	    }

	}
	
	private CircularBuffer readBuffer;
	private CircularBuffer writeBuffer;
	private boolean disconnected;
	
	public Channel(CircularBuffer readBuffer, CircularBuffer writeBuffer) {
		this.readBuffer = readBuffer;
		this.writeBuffer = writeBuffer;
		disconnected = false;
	}

	@Override
	public int read(byte[] bytes, int offset, int length){
		if(disconnected)
			throw new DisconnectedException("");
		
		int i = 0;
		while(i < length) {
			if(i == 0) {
				synchronized(readBuffer) {
					while(readBuffer.empty()) {
						try {
							readBuffer.wait();
						}catch(InterruptedException e) {
							
						}
					}
				}
			}
			byte b = readBuffer.pull();
			synchronized (readBuffer) {
				readBuffer.notify();
			}
			if((char)b == '\0') {
				disconnected = true;
				return i;
			}
			bytes[i + offset] = b;
			i++;
		}
		return i;
	}

	@Override
	public int write(byte[] bytes, int offset, int length){
		if(disconnected)
			throw new DisconnectedException("");
		
		int i = 0;
		while(i < length) {
			if(i == 0) {
				synchronized(writeBuffer) {
					while(writeBuffer.full()) {
						try {
							writeBuffer.wait();
						}catch(InterruptedException e) {
							
						}
					}
				}
			}
			writeBuffer.push(bytes[i + offset]);
			synchronized(writeBuffer) {
				writeBuffer.notify();
			}
			i++;
		}
		return i;
	}

	@Override
	public void disconnect(){
		if(disconnected)
			return;
		byte[] bytes = {(byte)'\0'};
		this.write(bytes, 0, 1);
		disconnected = true;
		
	}

	@Override
	public boolean disconnected() {
		return disconnected;
	}

	
}
