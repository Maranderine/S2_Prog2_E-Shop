package Domain.Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Domain.Eshop;

public class SocketVerwaltung {

  public final static int DEFAULT_PORT = 6789;
  private final Eshop eshop;
  private int port;
  private ServerSocket serverSocket;

  public SocketVerwaltung(Eshop eshop, int optPort) {

    this.eshop = eshop;
    this.port = (optPort == 0) ? DEFAULT_PORT : optPort;

    try {
      serverSocket = new ServerSocket(port);
      InetAddress ia = InetAddress.getLocalHost();
      System.out.println("Server - Host: " + ia.getHostName());
      System.out.println("Server - Server *" + ia.getHostAddress() + "* listening on port " + port);
    } catch (IOException e) {
      System.err.println("Server - ERROR - Error on Server-Sockets create: " + e);
      System.exit(1);
    }
  }

  public void acceptClientConnectRequests() {
    try {
      while (true) {

        System.out.println("Server - ...waiting...");
        Socket clientSocket = serverSocket.accept();

        // create client processor
        SocketProcessor process = new SocketProcessor(eshop, clientSocket);
        process.Process();

      }
    } catch (IOException e) {
      System.err.println("Server - ERROR - while listening: " + e);
      System.exit(1);
    }
  }
}
