package src.servers;

import java.io.IOException;

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 Autor.....: Vitor Reis
 Nome......: UDPServer.java
 Funcao....: Servidor UDP em Java
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

import java.net.*;
import java.util.List;

import src.domain.models.Client;
import src.domain.models.Server;
import src.presentation.Manager;

public class UDPServer extends Thread implements Server {
  private static UDPServer instance = null;
  private DatagramSocket socket;
  private int port = 6790;
  private Manager manager;

  @Override
  public void setManager(Manager manager) {
    this.manager = manager;
  }

  private UDPServer() {
    try {
      this.socket = new DatagramSocket(this.port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static UDPServer getInstance() {
    if (UDPServer.instance == null) {
      UDPServer.instance = new UDPServer();
    }

    return UDPServer.instance;
  }

  @Override
  public void init() {
    System.out.println("Starting UDP server on port: " + this.port);

    while (true) {
      byte[] receivedData = new byte[1024];
      DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);

      try {
        this.socket.receive(receivedPacket);

        System.out.println("Client: " + receivedPacket.getAddress() + " (UDP) connected.");

        new Thread(() -> {
          try {
            receive(receivedPacket);
          } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
          }
        }).start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }// fim

  @Override
  public void send(Client client, String data) {
    byte[] byteData = data.getBytes();
    int port = 6790;

    System.out.println("Sending " + data + " to " + client.getIp() + " Port: " + port);

    try {
      InetAddress address = InetAddress.getByName(client.getIp());

      DatagramPacket sendPacket = new DatagramPacket(byteData, byteData.length, address, port);
      this.socket.send(sendPacket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void receive(DatagramPacket connection) throws ClassNotFoundException, IOException {
    Client client = new Client(connection);

    String in = new String(connection.getData());
    System.out.println("Received from client: " + client.getIp() + " data: " + in);
    this.manager.execute(this, client, in);
  }

  public void run() {
    this.init();
  }

  @Override
  public String getIp() {
    return this.socket.getInetAddress().getHostAddress();
  }

  @Override
  public void sendToClients(List<Client> clients, String data) {
    for (Client client : clients) {
      send(client, data);
    }
  }
}// fim da classe servidorUDP
