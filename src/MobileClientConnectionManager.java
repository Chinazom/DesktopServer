import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MobileClientConnectionManager {

  static String SERVER_IP = "";

  public static void start() throws IOException {
    // TODO Auto-generated method stub
    ArrayList<MobileClient> mobileClientHandlersList = new ArrayList<MobileClient>();
    SERVER_IP = Utils.getLocalIpAddress();
    // create socket
    int port = 9090;
    boolean isOver = false;
    ServerSocket serverSocket = new ServerSocket(port);
    System.err.println("Started mobile server on port " + port);
    // waiting for connections, and process
    while (!isOver) {
      // a "blocking" call which waits until a connection is requested
      Socket clientSocket = serverSocket.accept();
      System.err.println("Accepted connection from mobile client");
      MobileClient handler = new MobileClient(clientSocket);
      handler.start();
      mobileClientHandlersList.add(handler);
    }
    serverSocket.close();
  }
}
