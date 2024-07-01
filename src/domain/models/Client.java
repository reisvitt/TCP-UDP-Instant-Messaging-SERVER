package src.domain.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;

public class Client {
  private String ip;
  private String serverIp;

  private DatagramPacket datagramPacket;
  private Socket socket;
  private ObjectOutputStream outputStream;
  private ObjectInputStream inputStream;

  public Client(Socket socket) throws IOException {
    this.socket = socket;
    this.ip = socket.getInetAddress().getHostAddress();
    this.serverIp = socket.getLocalAddress().getHostAddress();
    this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    this.inputStream = new ObjectInputStream(socket.getInputStream());
  }

  public Client(DatagramPacket datagramPacket) {
    this.datagramPacket = datagramPacket;
    this.ip = datagramPacket.getAddress().getHostAddress();
  }

  public void close() {
    try {
      if (outputStream != null)
        outputStream.close();

      if (inputStream != null)
        inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket getSocket() {
    return this.socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public ObjectOutputStream getOutputStream() {
    return outputStream;
  }

  public void setOutputStream(ObjectOutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public ObjectInputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(ObjectInputStream inputStream) {
    this.inputStream = inputStream;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public DatagramPacket getDatagramPacket() {
    return datagramPacket;
  }

  public void setDatagramPacket(DatagramPacket datagramPacket) {
    this.datagramPacket = datagramPacket;
  }
}