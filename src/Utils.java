import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {

  public static String getLocalIpAddress() throws UnknownHostException {
    String hostAddress = InetAddress.getLocalHost().getHostAddress();
    return hostAddress;
  }
}
