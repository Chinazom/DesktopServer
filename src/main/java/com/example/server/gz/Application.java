package com.example.server.gz;

import com.example.server.gz.service.GZTraCServerHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  private static final String NMEAFinal =
      "$GPGGA,082012.12,4915.6000000000,N,01143.2000000000,E,1,6,2.4,189.000,M,,M,2.4,0012*69";

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
   // GZTraCServerHandler.sendNewMessage(NMEAFinal);
  }
}
