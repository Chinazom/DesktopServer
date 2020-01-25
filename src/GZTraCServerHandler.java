import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class GZTraCServerHandler {

  public static byte[] bytes;
  static Socket socketClient;
  static int port = 1300;
  static String IPAddress;
  static String nmeaMessage;

  public static void GZTRacHandler(String messageFromServer) {
    try {
      IPAddress = Utils.getLocalIpAddress();
      socketClient = new Socket(Utils.getLocalIpAddress(), port);
      nmeaMessage = messageFromServer;
      new Thread(new SendingThread()).start();
      new Thread(new DataListenerThread()).start();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  static class SendingThread implements Runnable {

    @Override
    public void run() {
      try {
        //Send the message to the server
        OutputStream os = socketClient.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(nmeaMessage);
        bw.flush();
        System.out.println("Message sent to the server : " + nmeaMessage);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  static class DataListenerThread implements Runnable {

    int MAX_RECV_LEN = 8096;

    public void run() {
      bytes = new byte[MAX_RECV_LEN];
      int len;
      try {
        //Get the return message from the server
        InputStream is = socketClient.getInputStream();
        while ((len = is.read(bytes)) > 0) {
          Application.onNewMessage(bytes);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}