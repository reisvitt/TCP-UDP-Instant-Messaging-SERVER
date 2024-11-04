package src.domain.models;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;

public class Client {
  private String ip;
  private DatagramPacket datagramPacket;
  private Socket socket;

  public Client(Socket socket) throws IOException {
    this.socket = socket;
    this.ip = socket.getInetAddress().getHostAddress();
  }

  public Client(DatagramPacket datagramPacket) {
    this.datagramPacket = datagramPacket;
    this.ip = datagramPacket.getAddress().getHostAddress();
  }

  public void close() {
    try {
      if (this.socket != null) {
        this.socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket getSocket() {
    return this.socket;
  }

  public Boolean isConnected() {
    if (this.socket != null) {
      return this.socket.isConnected() && !this.socket.isClosed();
    }

    return false;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public DatagramPacket getDatagramPacket() {
    return datagramPacket;
  }

  public void setDatagramPacket(DatagramPacket datagramPacket) {
    this.datagramPacket = datagramPacket;
  }
}