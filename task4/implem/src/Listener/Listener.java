package Listener;

public interface Listener {
	
	public void received(byte[] msg);
	public void closed();
}
