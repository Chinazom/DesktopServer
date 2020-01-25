package com.example.server.gz.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GZTraCServerHandler {

  public static byte[] bytes;
  public static List<String> requests = new ArrayList<>();
  public static Map<String, List<Byte[]>> requestsResultMap = new HashMap<>();
  static Socket socketClient;
  static int port = 1300;
  static String IPAddress;
  static String nmeaMessage;

  public static void sendNewMessage(String messageFromClient, String id) {
    try {
      requests.add(id);
      requestsResultMap.put(id, new ArrayList<>());
      socketClient = new Socket(Utils.getLocalIpAddress(), port);
      nmeaMessage = messageFromClient;
      new Thread(new SendingThread()).start();
      new Thread(new DataListenerThread()).start();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  static void onNewMessage(byte[] bytes) {
    String id = requests.get(requests.size() - 1);
    List<Byte[]> bytes1 = requestsResultMap.get(requests.get(requests.size() - 1));
    int i = 0;
    Byte[] byteObjects = new Byte[bytes.length];
    for (byte b : bytes) byteObjects[i++] = b;
    bytes1.add(byteObjects);
    requestsResultMap.put(id, bytes1);
  }

  static class SendingThread implements Runnable {

    @Override
    public void run() {
      try {
        // Send the message to the server
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
        // Get the return message from the server
        InputStream is = socketClient.getInputStream();
        while ((len = is.read(bytes)) > 0) {
          onNewMessage(bytes);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
