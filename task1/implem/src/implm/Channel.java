package implm;

public class Channel extends Interface.Channel{
	
	public class DisconnectedException extends RuntimeException {

	    public DisconnectedException(String s) {
	        super("The channel has been disconnected.");
	    }

	}
	
	private static final String SHUTDOWN_MESSAGE = "SHUTDOWN";
	
	private CircularBuffer readBuffer;
	private CircularBuffer writeBuffer;
	private boolean disconnected;
	private StringBuilder stopBuffer;
	
	public Channel(CircularBuffer readBuffer, CircularBuffer writeBuffer) {
		this.readBuffer = readBuffer;
		this.writeBuffer = writeBuffer;
		disconnected = false;
		stopBuffer = new StringBuilder();
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
			
			try {
				byte b = readBuffer.pull();
				synchronized (readBuffer) {
					readBuffer.notify();
				}
				bytes[i + offset] = b;
				i++;
			}
			catch (IllegalStateException e) {
				break;
			}
		}
		if(isShutDown(bytes, offset, i))
			disconnected = true;
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
			
			try {
				writeBuffer.push(bytes[i + offset]);
				synchronized(writeBuffer) {
					writeBuffer.notify();
				}
				i++;
			}
			catch(IllegalStateException e) {
				return i;
			}
		}
		return i;
	}

	@Override
	public void disconnect(){
		if(disconnected)
			return;
		byte[] bytes = SHUTDOWN_MESSAGE.getBytes();
		int i = 0;
		while(i != bytes.length)
			i += this.write(bytes, i, bytes.length - i);
		disconnected = true;
		
	}
	
	private boolean isShutDown(byte[] bytes, int offset, int length) {
		stopBuffer.append(new String(bytes, offset, length));
		if(stopBuffer.length() >= SHUTDOWN_MESSAGE.length()) {
			String s = stopBuffer.toString();
			if(s.contains(SHUTDOWN_MESSAGE))
				return true;
			stopBuffer = new StringBuilder();
			return false;
		}
		return false;
	}

	@Override
	public boolean disconnected() {
		return disconnected;
	}
}
