package src.servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

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
    if (socket == null) {
      System.out.println("Socket of client " + client.getIp() + " is null.");
      return;
    }
    if (socket.isConnected()) {
      System.out.println("(TCP) Sending " + data + " to " + client.getIp());

      try {
        OutputStream output = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeUTF(data);
        oos.flush();
      } catch (IOException e) {
        System.out.println("(TCP) Failure sending to " + client.getIp());
        e.printStackTrace();
      }
    } else {
      System.out.println("(TCP) Socket of client " + client.getIp() + " is not connected.");
      client.close();
    }
  }

  public void receive(Socket connection) throws IOException {
    Client client = new Client(connection);
    while (client.isConnected()) {
      try {
        InputStream is = connection.getInputStream();

        ObjectInputStream oip = new ObjectInputStream(is);
        String in = oip.readUTF();

        System.out.println("(TCP) Received from client: " + client.getIp() + " data: " + in);
        this.manager.execute(this, client, in);
      } catch (Exception e) {
        this.manager.execute(this, client, "LEAVE ALL GROUPS");
        client.close();
      }
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
