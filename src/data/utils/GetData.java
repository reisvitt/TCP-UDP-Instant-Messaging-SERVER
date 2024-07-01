package src.data.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.Socket;

public class GetData {
  public static String fromTCP(Socket connection) throws IOException, ClassNotFoundException {
    ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
    String data = (String) input.readObject();
    return data;
  }

  public static String fromUDP(DatagramPacket packet) {
    String data = new String(packet.getData());
    return data;
  }

  public static String getUtil(String data) {
    String remove = data.split(" ")[0];
    return data.replaceAll(remove, "").trim();
  }
}
