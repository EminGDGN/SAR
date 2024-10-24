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
			
			try {
				byte b = readBuffer.pull();
				bytes[i + offset] = b;
				i++;
			}
			catch (IllegalStateException e) {
				break;
			}
		}
		return i;
	}

	@Override
	public int write(byte[] bytes, int offset, int length){
		if(disconnected)
			throw new DisconnectedException("");
		
		int i = 0;
		while(i < length) {
			try {
				writeBuffer.push(bytes[i + offset]);
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
		disconnected = true;
		
	}

	@Override
	public boolean disconnected() {
		return disconnected;
	}
}
