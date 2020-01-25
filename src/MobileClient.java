import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MobileClient extends Thread {

  String message;
  private Socket socket;
  private InputStream in;
  private OutputStream out;

  public MobileClient(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      in = socket.getInputStream();
      out = socket.getOutputStream();
      checkForIncomingRequests();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void onNewRequest(String message) {
    Application.send(message, this);
  }

  public String checkForIncomingRequests() {
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(in);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      message = bufferedReader.readLine();
      System.out.println("Message received from android client : " + message);
      onNewRequest(message);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return message;
  }

  public void writeMessage(byte[] bytes) throws IOException {
    System.err.println("Incoming packed message from server!!!!!!");
  }


  public void disconnect() {
    try {
      out.close();
      in.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}