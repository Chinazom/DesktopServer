package interGZTRaC;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ServerDesktop {
	static String SERVER_IP = "";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 ArrayList<ClientHandler> clientHandlersList = new ArrayList<ClientHandler>();
		 
		 SERVER_IP = getLocalIpAddress();

	        // create socket
	        int port = 9090;
	        boolean isOver = false;
	        ServerSocket serverSocket = new ServerSocket(port);
	        System.err.println("Started server on port " + port);

	        // waiting for connections, and process
	        while (!isOver) {

	            // a "blocking" call which waits until a connection is requested
	            Socket clientSocket = serverSocket.accept();
	            System.err.println("Accepted connection from client");

	            ClientHandler handler = new ClientHandler(clientSocket);
	            handler.start();
	            clientHandlersList.add(handler);
	        }

	        serverSocket.close();
	}
	// get the ip address of the host system
	static String getLocalIpAddress() throws UnknownHostException {
		
        String IPAddress;
        int Port = 9090;
      IPAddress = InetAddress.getLocalHost().getHostAddress();
      System.out.println("Your current IP Address : " + IPAddress);
      System.out.println("Your current PORT : " + Port);
      return IPAddress;
	}
}
