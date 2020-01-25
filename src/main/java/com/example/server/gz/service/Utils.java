package com.example.server.gz.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {

  public static String getLocalIpAddress() throws UnknownHostException {
    String hostAddress = InetAddress.getLocalHost().getHostAddress();
    System.out.println("IP Address is " + hostAddress);
    return hostAddress;
  }

}
