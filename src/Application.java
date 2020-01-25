import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

  static String nmea = "$GPGGA,082012.12,4915.6000000000,N,01143.2000000000,E,1,6,2.4,189.000,M,,M,2.4,0012*69";
  static List<byte[]> messagePack = new ArrayList<>();
  static MobileClient currentClient;

  public static void main(String[] args) throws IOException {
    System.out.println("Hello World!");
    MobileClientConnectionManager.start();
  }

  public static void send(String message, MobileClient mMobileClient) {
    currentClient = mMobileClient;
    GZTraCServerHandler.GZTRacHandler(message);
  }

  public static void onNewMessage(byte[] bytes) throws IOException {
    System.out.println("New Message From Server");
    System.out.println(bytes.length);
    messagePack.add(bytes);
    if (messagePack.size() == 3) {
      System.out.println(
          "Should send this to android, message size is full. Size is : " + messagePack.size());
      messagePack = new ArrayList<>();
      if (currentClient != null) {
        currentClient.writeMessage(bytes);
      }
    }
  }
}
