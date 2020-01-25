package interGZTRaC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ClientHandler extends Thread {

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    ServerSocket serverSocket;
	String message;
	String ech;
	String IPAddress;
	int port = 1300;
	byte[] byteServer = new byte[8086];

    GZTRacHandler GZTRacHandler = new GZTRacHandler ();
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {        
            in = socket.getInputStream();
            out =socket.getOutputStream();          
            String MessageFromAndroid = readMessageFromAndroid();
            GZTRacHandler.GZTRacHandl(MessageFromAndroid); 
            GZTRacHandler.Thread1 Thread = GZTRacHandler.new Thread1();
            GZTRacHandler.Thread2 Thread2 = GZTRacHandler.new Thread2();
            Thread thread = new Thread(Thread); 
            Thread thread2 = new Thread(Thread2); 
    		thread.start();
    		thread2.start();
    		TimeUnit.SECONDS.sleep(1);
    		writeMessage();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public String readMessageFromAndroid(){
        try {
        	InputStreamReader inputStreamReader = new InputStreamReader(in);
        	BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
        	message = bufferedReader.readLine();
        	System.out.println("Message received from the server : " + message);
           

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

	  public void writeMessage() throws IOException{ 
		  byteServer = interGZTRaC.GZTRacHandler.bytes;
		  String echoStr = new String(byteServer);
    	  System.out.println(echoStr);
	  }
	  
	  
	  
	  public void disconnect(){
		  try { 
			  out.close(); 
			  in.close();
			  socket.close(); 
		  } catch (IOException e) {
			  e.printStackTrace(); 
			  } 
		  }
	 

}
