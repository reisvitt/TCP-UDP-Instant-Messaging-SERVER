package src.domain.models;

import java.util.List;

import src.presentation.Manager;

public interface Server {
  void init() throws Exception;

  void send(Client client, String data);

  void setManager(Manager manager);

  String getIp();

  void sendToClients(List<Client> clients, String data);
}