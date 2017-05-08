import java.net.*;
public class Player {
	
	private String name;
	private InetSocketAddress address;
	
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Player(String name, String address, String port) {
		this.name = name;
		this.address = new InetSocketAddress(address,Integer.parseInt(port));
		
		
	}
	
	public  InetSocketAddress getInetSocketAddress() {
		return this.address;
	}
	
	public Socket getSocket() {
		
		try {
			return  new Socket(this.address.getAddress(),this.address.getPort() );    
		}catch(Exception ex) {
			System.err.append(ex.toString());
		}
		return null;
	}
	

}
