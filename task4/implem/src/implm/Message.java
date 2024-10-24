package implm;

public class Message {
	
	private int sizeLen;
	private byte[] len;
	private byte[] buffer;
	private int i;
	private int size;
	private String state;
	
	public Message() {
		state = "read_size_len";
		i = 0;
	}
	
	public Message(byte[] data) {
		len = encodeLength(data.length);
		sizeLen = len.length;
		
		buffer = new byte[1 + len.length + data.length];
		buffer[0] = (byte)sizeLen;
		System.arraycopy(len, 0, buffer, 1, len.length);
		System.arraycopy(data, 0, buffer, 1 + len.length, data.length);
		
		i = 0;
		state = "write_message";
	}
	
	public void addData(Channel c) {
		if(state.equals("write_message"))
			throw new IllegalStateException("Message object is used to write, not to read");
		
		byte[] data = new byte[Broker.DEFAULT_CAPACITY];
		int length = c.read(data, 0, Broker.DEFAULT_CAPACITY);
		int k = 0;
		
		if(state.equals("read_size_len")) {
			sizeLen = data[0];
			len = new byte[sizeLen];
			state = "read_len";
			k++;
		}
		if(state.equals("read_len")) {
			for(;i < sizeLen && k < length; i++) {
				len[i] = data[k++];
			}
			if(i == sizeLen) {
				size = decodeLength(len, sizeLen);
				buffer = new byte[size];
				i = 0;
				state = "read_data";
			}
		}
		if(state.equals("read_data")) {
			for(; i < size && k < length; i++) {
				buffer[i] = data[k++];
			}
			if(i == size) {
				state = "fully_read";
			}
		}
	}
	
	public void write(Channel c) {
		if(!state.equals("write_message"))
			throw new IllegalStateException("Message object is used to read, not to write");
		
		if(buffer.length > i)
			i = c.write(buffer, i, buffer.length - i);
	}
	
	public byte[] getData() {
		return buffer;
	}
	
	public boolean fullyRead() {
		return state.equals("fully_read");
	}
	
	public boolean fullyWrote() {
		return buffer.length == i;
	}
	
	private byte[] encodeLength(int length) {
		if(length < 256) {
			byte[] result = {(byte)length};
			return result;
		}
		
		byte[] result;
		if(length < 65536) {
			result = new byte[2];
			result[0] = (byte) (length >> 8);
			result[1] = (byte) length;
			return result;
		}
		if(length < 16777216) {
			result = new byte[3];
			result[0] = (byte) (length >> 16);
			result[1] = (byte) (length >> 8);
			result[2] = (byte) length;
		}
		
		result = new byte[4];
		result[0] = (byte) (length >> 24);
		result[1] = (byte) (length >> 16);
		result[2] = (byte) (length >> 8);
		result[3] = (byte) length;
		return result;
	}
	
	private int decodeLength(byte[] size, int len) {
		int result = 0;
	    for (int i = 0; i < len; i++) {
	        result |= (size[i] & 0xFF) << ((len - i - 1) * 8);
	    }
	    return result;
	}
}
