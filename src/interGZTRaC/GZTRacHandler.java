package interGZTRaC;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;


public class GZTRacHandler {
	Socket MyClient;
	DataOutputStream input;
	public static byte[] bytes;
	int port = 1300;
	String IPAddress;
	String nmea;
	String echoStr;
	
	public void GZTRacHandl(String messageFromServer) {
		
		
		// establishing  connection 
	    try {
	    	IPAddress  = ServerDesktop.getLocalIpAddress();
	    	MyClient = new Socket(IPAddress, port );	    	
	    	nmea = messageFromServer;
	        System.out.println(messageFromServer);
	     
	    }
	    catch (IOException e) {
	        System.out.println(e); 
	    }
	}
	
	class Thread1 implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			 try {
				 //Send the message to the server
		            OutputStream os = MyClient.getOutputStream();
		            OutputStreamWriter osw = new OutputStreamWriter(os);
		            BufferedWriter bw = new BufferedWriter(osw);	 
		            //String nmea = "$GPGGA,082012.12,4915.6000000000,N,01143.2000000000,E,1,6,2.4,189.000,M,,M,2.4,0012*69";
		            System.out.println(nmea);
		            bw.write(nmea);
		            bw.flush();
		            System.out.println("Message sent to the server : "+nmea);
		            System.out.println("Message received from the server : ");
	         } catch (IOException e) {
	         e.printStackTrace();
	         }

	}
}
	
	class Thread2 implements Runnable {
		 int MAX_RECV_LEN = 8096;
			int MAX_MSG_LEN = 4096;	
	     	int received = 0;                 // The number of bytes received
	        int msgSize = MAX_RECV_LEN;       // The number of bytes wanted to receive
	        int numBytes = 0;                 // The number of bytes currently received
	     	int totalRecvNum = 0;
	     	private final Logger logger = Logger.getLogger("CLIENT");
	     	
		      public void run() {
		    	 bytes = new byte[MAX_RECV_LEN];		    	 
	     	     int len;
		            try {
		            		
					  //Get the return message from the server 		            		
		            	     InputStream is = MyClient.getInputStream(); 
		            	        while((len = is.read(bytes)) > 0) {
		            	            echoStr = new String(bytes, 0, len);		            	            
		            	            System.out.println("Message received from the server : " + echoStr);
		            	            System.out.println("Message received from the server : " + len);         	           
		        
		            	        }
		            
		            } catch (IOException e) {
		               e.printStackTrace();
		            }
		         }
		      
		      }	 
	
}
