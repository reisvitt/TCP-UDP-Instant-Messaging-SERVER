package src.servers;

import java.io.IOException;

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 Autor.....: Vitor Reis
 Nome......: TCPServer.java
 Funcao....: Servidor UDP em Java
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

import java.net.*;
import java.util.List;

import src.domain.models.Client;
import src.domain.models.Server;
import src.presentation.Manager;

public class TCPServer extends Thread implements Server {
  private static TCPServer instance = null;
  private ServerSocket server;
  private int port = 6789;
  private Manager manager;

  @Override
  public void setManager(Manager manager) {
    this.manager = manager;
  }

  private TCPServer() {
    try {
      this.server = new ServerSocket(port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static TCPServer getInstance() {
    if (TCPServer.instance == null) {
      TCPServer.instance = new TCPServer();
    }

    return TCPServer.instance;
  }

  @Override
  public void init() {
    System.out.println("Starting TCP server on port: " + this.port);

    try {
      while (true) {
        Socket clientConection = server.accept();

        System.out.println("Client: " + clientConection.getInetAddress().getHostAddress() + " Port: "
            + clientConection.getLocalPort() + " (TCP) connected.");

        new Thread(() -> {
          try {
            receive(clientConection);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }// fim do metodo start

  @Override
  public void send(Client client, String data) {
    Socket socket = client.getSocket();
    if (socket.isConnected() && !socket.isClosed()) {
      System.out.println("Sending " + data + " to " + client.getIp());

      try {
        client.getOutputStream().writeObject(data);
        client.getOutputStream().flush();
      } catch (IOException e) {
        System.out.println("Failure sending to " + client.getIp());
        e.printStackTrace();
      }
    }
  }

  public void receive(Socket connection) throws IOException {
    Client client = new Client(connection);
    try {
      while (true) {
        String in = (String) client.getInputStream().readObject();
        System.out.println("Received from client: " + client.getIp() + " data: " + in);
        this.manager.execute(this, client, in);
      }
    } catch (Exception e) {
      this.manager.execute(this, client, "LEAVE ALL GROUPS");
      client.close();
    }
  }

  public void run() {
    this.init();
  }

  @Override
  public String getIp() {
    return this.server.getInetAddress().getHostAddress();
  }

  @Override
  public void sendToClients(List<Client> clients, String data) {
    for (Client client : clients) {
      send(client, data);
    }
  }
}// fim da classe TCPServer
