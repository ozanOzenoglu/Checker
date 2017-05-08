import java.net.*;
import java.io.*;

import javax.swing.SwingUtilities;

public class Server extends Thread{
	
	static ServerSocket serverSocket;
	static Socket clientSocket;
	int myPort ;
	String userName;
	
	public Server(int port,String userName) {
		try{
			this.userName = userName;
			serverSocket = new ServerSocket(port);
			this.myPort = serverSocket.getLocalPort();
		}catch(Exception ex){
			System.err.append(ex.toString());
		}
	}
	
	
	
	
	public void run() {
		try{
			
			
	
		System.out.print("Client Server dinlemeye basladi\n");
		clientSocket = serverSocket.accept();
		

        new Game(clientSocket,userName,false,"red"); // false mean first move belongs client
           
		 
		 
		
		}catch(Exception ex){
			System.err.append(ex.toString());
		}
		
		
	}
	
	@SuppressWarnings("static-access")
	public int getCurrentPort() {
		
			return myPort;
	}

}
