package implm;


import java.util.Arrays;
import java.util.HashMap;

public class Message {
	
	private static HashMap<Integer, Message> messages = new HashMap<Integer, Message>();

	private byte[] buffer;
	private int offset;
	private int length;
	private int lengthSize;
	private int hash;
	
	public Message(byte[] buffer, int offset, int length) {
		synchronized(messages) {
			byte[] lenBytes = encodeLength(length);
			this.lengthSize = lenBytes.length;
			this.buffer = concat(lenBytes, lengthSize, buffer, length);
			this.offset = offset;
			this.length = length + lengthSize;
			this.hash = Arrays.hashCode(buffer);
			messages.put(hash, this);
		}
	}
	
	public Message(byte[] buffer) {
		this(buffer, 0, buffer.length);
	}
	
	public Message() {
		buffer = null;
		offset = 0;
		lengthSize = 0;
		length = 0;
	}
	
	public byte[] getBuffer(int offset, int length) {
		byte[] content = new byte[length];
		for(int i = 0; i < length; i++) {
			content[i] = buffer[i+offset];
		}
		return content;
	}
	
	public byte[] getBuffer() {
		return getBuffer(0, length);
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getLengthSize() {
		return lengthSize;
	}
	
	public boolean done() {
		return length == this.buffer.length - offset;
	}
	
	public void addBuffer(byte[] buffer) {
		if(lengthSize == 0) {
			lengthSize = buffer[0];
			offset = 1;
		}
		
		if(this.buffer == null) {
			this.buffer = buffer;
		}
		else {
			this.buffer = concat(this.buffer, this.buffer.length, buffer, buffer.length);
		}
		
		if(length == 0 && this.buffer.length - 1 >= lengthSize) {
			length = decodeLength(this.buffer, 1, lengthSize);
			offset += lengthSize;
		}
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setLengthSize(int lengthSize) {
		this.lengthSize = lengthSize;
	}
	
	public static Message getMessage(byte[] buffer) {
		synchronized(messages) {
			int hash = Arrays.hashCode(buffer);
			return messages.get(hash);
		}
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
	
	private int decodeLength(byte[] size, int offset, int len) {
		int result = 0;
	    for (int i = 0; i < len; i++) {
	        result |= (size[i + offset] & 0xFF) << ((len - i - 1) * 8);
	    }
	    return result;
	}
	
	private byte[] concat(byte[] b1, int l1, byte[] b2, int l2) {
		byte[] buffer = new byte[l1 + l2];
		System.arraycopy(b1, 0, buffer, 0, l1);
		System.arraycopy(b2, 0, buffer, l1, l2);
		return buffer;
	}
}
